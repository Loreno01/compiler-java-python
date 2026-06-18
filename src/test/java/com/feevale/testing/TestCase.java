package com.feevale.testing;

public class TestCase {
    private final String className;
    private final String description;
    private final String mainBody;

    public TestCase(String className, String description, String mainBody) {
        this.className = className;
        this.description = description;
        this.mainBody = mainBody;
    }

    public String getClassName() {
        return className;
    }

    public String getDescription() {
        return description;
    }

    public String getMainBody() {
        return mainBody;
    }

    public String toJavaSource() {
        return "public class " + className + " {\n"
                + "    public static void main(String[] args) {\n"
                + indentBody(mainBody)
                + "    }\n"
                + "}\n";
    }

    private static String indentBody(String body) {
        String[] lines = body.split("\n");
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append("        ").append(line.trim()).append("\n");
        }
        return sb.toString();
    }
}
