package com.feevale.testing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.feevale.Compiler;

public class ComparisonTestRunner {

    private static final Path PROJECT_ROOT = Paths.get("").toAbsolutePath().normalize();
    private static final Path TESTING_DIR = PROJECT_ROOT
            .resolve("src").resolve("test").resolve("java")
            .resolve("com").resolve("feevale").resolve("testing").resolve("tests");
    private static final Path CASES_DIR = TESTING_DIR.resolve("cases");
    private static final Path GENERATED_DIR = TESTING_DIR.resolve("generated");
    private static final Path JAVA_BUILD_DIR = PROJECT_ROOT.resolve("target").resolve("comparison-test-classes");
    private static final long PROCESS_TIMEOUT_SECONDS = 15;

    public static void main(String[] args) throws Exception {
        ensureDirectories();

        List<TestCase> testCases = TestCases.all();
        List<TestResult> results = new ArrayList<>();

        writeJavaSources(testCases);

        System.out.println("\nExecutando " + testCases.size() + " testes de comparacao Java x Python...\n");

        for (TestCase testCase : testCases) {
            TestResult result = runSingleTest(testCase);
            results.add(result);
            printTestResult(result);
        }

        printSummary(results);

        long failed = results.stream().filter(result -> !result.isPassed()).count();
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void ensureDirectories() throws IOException {
        Files.createDirectories(CASES_DIR);
        Files.createDirectories(GENERATED_DIR);
        Files.createDirectories(JAVA_BUILD_DIR);
    }

    private static void writeJavaSources(List<TestCase> testCases) throws IOException {
        for (TestCase testCase : testCases) {
            Path javaFile = CASES_DIR.resolve(testCase.getClassName() + ".java");
            Files.writeString(javaFile, testCase.toJavaSource(), StandardCharsets.UTF_8);
        }
    }

    private static TestResult runSingleTest(TestCase testCase) {
        String name = testCase.getClassName();
        Path javaSource = CASES_DIR.resolve(name + ".java");
        Path pythonOutput = GENERATED_DIR.resolve(name + ".py");

        try {
            Compiler.compileFile(javaSource.toFile(), pythonOutput.toFile());
        } catch (Exception ex) {
            return TestResult.failed(name, testCase.getDescription(), "Falha na compilacao Java->Python: " + ex.getMessage());
        }

        ProcessResult javaResult;
        try {
            javaResult = runJava(name, javaSource);
        } catch (Exception ex) {
            return TestResult.failed(name, testCase.getDescription(), "Falha ao executar Java: " + ex.getMessage());
        }

        if (javaResult.exitCode != 0) {
            return TestResult.failed(name, testCase.getDescription(),
                    "Java terminou com codigo " + javaResult.exitCode + "\n" + javaResult.stderr);
        }

        ProcessResult pythonResult;
        try {
            pythonResult = runPython(pythonOutput);
        } catch (Exception ex) {
            return TestResult.failed(name, testCase.getDescription(), "Falha ao executar Python: " + ex.getMessage());
        }

        if (pythonResult.exitCode != 0) {
            return TestResult.failed(name, testCase.getDescription(),
                    "Python terminou com codigo " + pythonResult.exitCode + "\n" + pythonResult.stderr);
        }

        if (!outputsEquivalent(javaResult.stdout, pythonResult.stdout)) {
            return TestResult.failed(name, testCase.getDescription(),
                    "Saida diferente\nJava:\n" + javaResult.stdout + "\nPython:\n" + pythonResult.stdout);
        }

        return TestResult.passed(name, testCase.getDescription());
    }

    private static ProcessResult runJava(String className, Path javaSource) throws IOException, InterruptedException {
        compileJava(javaSource);
        String javaBin = Paths.get(System.getProperty("java.home"), "bin", executableName("java")).toString();
        return runProcess(
                new ProcessBuilder(javaBin, "-cp", JAVA_BUILD_DIR.toString(), className),
                JAVA_BUILD_DIR);
    }

    private static void compileJava(Path javaSource) throws IOException, InterruptedException {
        String javacBin = Paths.get(System.getProperty("java.home"), "bin", executableName("javac")).toString();
        ProcessResult result = runProcess(
                new ProcessBuilder(javacBin, "-encoding", "UTF-8", "-d", JAVA_BUILD_DIR.toString(),
                        javaSource.toString()),
                PROJECT_ROOT);
        if (result.exitCode != 0) {
            throw new IOException("javac falhou:\n" + result.stderr);
        }
    }

    private static ProcessResult runPython(Path pythonScript) throws IOException, InterruptedException {
        String pythonCommand = findPythonCommand();
        return runProcess(new ProcessBuilder(pythonCommand, pythonScript.toString()), PROJECT_ROOT);
    }

    private static String findPythonCommand() {
        if (isCommandAvailable("python")) {
            return "python";
        }
        if (isCommandAvailable("python3")) {
            return "python3";
        }
        throw new IllegalStateException("Python nao encontrado no PATH (tente instalar python ou python3).");
    }

    private static boolean isCommandAvailable(String command) {
        try {
            Process process = new ProcessBuilder(command, "--version").redirectErrorStream(true).start();
            return process.waitFor(5, TimeUnit.SECONDS) && process.exitValue() == 0;
        } catch (Exception ex) {
            return false;
        }
    }

    private static ProcessResult runProcess(ProcessBuilder builder, Path workDir)
            throws IOException, InterruptedException {
        builder.directory(workDir.toFile());
        builder.redirectErrorStream(false);
        Process process = builder.start();

        String stdout = readStream(process.getInputStream());
        String stderr = readStream(process.getErrorStream());

        if (!process.waitFor(PROCESS_TIMEOUT_SECONDS, TimeUnit.SECONDS)) {
            process.destroyForcibly();
            throw new IOException("Processo excedeu timeout de " + PROCESS_TIMEOUT_SECONDS + "s");
        }

        return new ProcessResult(process.exitValue(), stdout, stderr);
    }

    private static String readStream(java.io.InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(line);
            }
        }
        return sb.toString();
    }

    static boolean outputsEquivalent(String javaOutput, String pythonOutput) {
        List<String> javaLines = splitLines(javaOutput);
        List<String> pythonLines = splitLines(pythonOutput);

        if (javaLines.size() != pythonLines.size()) {
            return false;
        }

        for (int i = 0; i < javaLines.size(); i++) {
            if (!linesEquivalent(javaLines.get(i), pythonLines.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<String> splitLines(String output) {
        if (output == null || output.isEmpty()) {
            return List.of();
        }
        String normalized = output.replace("\r\n", "\n").replace('\r', '\n');
        if (normalized.endsWith("\n")) {
            normalized = normalized.substring(0, normalized.length() - 1);
        }
        if (normalized.isEmpty()) {
            return List.of("");
        }
        return List.of(normalized.split("\n", -1));
    }

    static boolean linesEquivalent(String javaLine, String pythonLine) {
        if (javaLine.equals(pythonLine)) {
            return true;
        }

        if (javaLine.equalsIgnoreCase(pythonLine)) {
            return isBooleanLiteral(javaLine);
        }

        if (numericEquivalent(javaLine, pythonLine)) {
            return true;
        }

        return false;
    }

    private static boolean isBooleanLiteral(String value) {
        String lower = value.toLowerCase(Locale.ROOT);
        return lower.equals("true") || lower.equals("false");
    }

    private static boolean numericEquivalent(String left, String right) {
        try {
            double a = Double.parseDouble(left);
            double b = Double.parseDouble(right);
            return Math.abs(a - b) < 1e-9;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private static String executableName(String command) {
        if (System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win")) {
            return command + ".exe";
        }
        return command;
    }

    private static void printTestResult(TestResult result) {
        if (result.isPassed()) {
            System.out.println("[ OK ] " + result.getName() + " - " + result.getDescription());
        } else {
            System.out.println("[FAIL] " + result.getName() + " - " + result.getDescription());
            System.out.println("       " + result.getDetails().replace("\n", "\n       "));
        }
        System.out.flush();
    }

    private static void printSummary(List<TestResult> results) {
        long passed = results.stream().filter(TestResult::isPassed).count();
        long failed = results.size() - passed;

        System.out.println();
        System.out.println("=".repeat(72));
        System.out.println("RESUMO DOS TESTES DE COMPARACAO");
        System.out.println("=".repeat(72));
        System.out.println("Total:  " + results.size());
        System.out.println("Passou: " + passed);
        System.out.println("Falhou: " + failed);

        if (failed > 0) {
            System.out.println();
            System.out.println("Detalhes das falhas:");
            System.out.println("-".repeat(72));
            for (TestResult result : results) {
                if (!result.isPassed()) {
                    System.out.println("[FAIL] " + result.getName() + " - " + result.getDescription());
                    System.out.println(result.getDetails());
                    System.out.println("-".repeat(72));
                }
            }
        }
    }

    private static final class ProcessResult {
        private final int exitCode;
        private final String stdout;
        private final String stderr;

        private ProcessResult(int exitCode, String stdout, String stderr) {
            this.exitCode = exitCode;
            this.stdout = stdout;
            this.stderr = stderr;
        }
    }

    private static final class TestResult {
        private final String name;
        private final String description;
        private final boolean passed;
        private final String details;

        private TestResult(String name, String description, boolean passed, String details) {
            this.name = name;
            this.description = description;
            this.passed = passed;
            this.details = details;
        }

        static TestResult passed(String name, String description) {
            return new TestResult(name, description, true, "");
        }

        static TestResult failed(String name, String description, String details) {
            return new TestResult(name, description, false, details);
        }

        String getName() {
            return name;
        }

        String getDescription() {
            return description;
        }

        boolean isPassed() {
            return passed;
        }

        String getDetails() {
            return details;
        }
    }
}
