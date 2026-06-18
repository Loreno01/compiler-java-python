package com.feevale.testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class TestCases {

    private TestCases() {
    }

    public static List<TestCase> all() {
        List<TestCase> cases = new ArrayList<>();
        int index = 1;

        index = addBasicPrintTests(cases, index);
        index = addStringTests(cases, index);
        index = addDeclarationTests(cases, index);
        index = addArithmeticTests(cases, index);
        index = addAssignmentTests(cases, index);
        index = addIfTests(cases, index);
        index = addComparisonTests(cases, index);
        index = addLogicalTests(cases, index);
        index = addWhileTests(cases, index);
        index = addForTests(cases, index);
        index = addSwitchTests(cases, index);
        index = addIncrementTests(cases, index);
        index = addCommentTests(cases, index);
        index = addComplexTests(cases, index);
        index = addBooleanTests(cases, index);
        index = addBehaviorTests(cases, index);

        if (cases.size() != 165) {
            throw new IllegalStateException("Esperava 165 testes, encontrou " + cases.size());
        }

        return Collections.unmodifiableList(cases);
    }

    private static int addBasicPrintTests(List<TestCase> cases, int index) {
        for (int value = 0; value < 10; value++) {
            cases.add(new TestCase(
                    formatName(index++),
                    "println inteiro " + value,
                    "System.out.println(" + value + ");"));
        }
        return index;
    }

    private static int addStringTests(List<TestCase> cases, int index) {
        String[] literals = {
                "\"alpha\"",
                "\"beta\"",
                "\"gamma\"",
                "\"delta\"",
                "\"epsilon\""
        };
        for (String literal : literals) {
            cases.add(new TestCase(
                    formatName(index++),
                    "println string " + literal,
                    "System.out.println(" + literal + ");"));
        }
        return index;
    }

    private static int addDeclarationTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "declara int zero",
                "int x = 0;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara double zero",
                "double x = 0;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara String vazia",
                "String x = \"\";\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara boolean false",
                "boolean x = false;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara int inicializado",
                "int x = 7;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara double inicializado",
                "double x = 2.5;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara String inicializado",
                "String x = \"ok\";\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "declara boolean true",
                "boolean x = true;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "multiplas declaracoes",
                "int a, b, c;\na = 1;\nb = 2;\nc = 3;\nSystem.out.println(a + b + c);"));
        cases.add(new TestCase(formatName(index++), "declaracao com lista e init",
                "int a = 4, b = 5;\nSystem.out.println(a * b);"));
        return index;
    }

    private static int addArithmeticTests(List<TestCase> cases, int index) {
        int[][] samples = {
                { 2, 3, 5 },
                { 10, 4, 14 },
                { 7, 7, 14 },
                { 0, 9, 9 },
                { 15, 6, 21 }
        };
        for (int[] sample : samples) {
            int a = sample[0];
            int b = sample[1];
            int sum = sample[2];
            cases.add(new TestCase(formatName(index++), "soma " + a + "+" + b,
                    "System.out.println(" + a + " + " + b + ");"));
            cases.add(new TestCase(formatName(index++), "subtracao",
                    "System.out.println(" + sum + " - " + b + ");"));
            cases.add(new TestCase(formatName(index++), "multiplicacao",
                    "System.out.println(" + a + " * " + b + ");"));
            cases.add(new TestCase(formatName(index++), "modulo",
                    "System.out.println(" + sum + " % " + b + ");"));
        }
        cases.add(new TestCase(formatName(index++), "expressao com parenteses",
                "System.out.println((2 + 3) * 4);"));
        return index;
    }

    private static int addAssignmentTests(List<TestCase> cases, int index) {
        for (int start = 1; start <= 5; start++) {
            cases.add(new TestCase(formatName(index++), "atribuicao simples " + start,
                    "int x;\nx = " + start + ";\nSystem.out.println(x);"));
        }
        return index;
    }

    private static int addIfTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "if verdadeiro",
                "int x = 5;\nif (x > 0) {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "if falso",
                "int x = 0;\nif (x > 0) {\n    System.out.println(1);\n}\nSystem.out.println(0);"));
        cases.add(new TestCase(formatName(index++), "if else then",
                "int x = 3;\nif (x > 5) {\n    System.out.println(0);\n} else {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "if else else branch",
                "int x = 9;\nif (x > 5) {\n    System.out.println(2);\n} else {\n    System.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "if encadeado",
                "int x = 2;\nif (x > 0) {\n    if (x < 5) {\n        System.out.println(3);\n    }\n}"));
        return index;
    }

    private static int addComparisonTests(List<TestCase> cases, int index) {
        String[][] ops = {
                { ">", "5", "3", "1" },
                { "<", "2", "8", "1" },
                { ">=", "5", "5", "1" },
                { "<=", "4", "4", "1" },
                { "==", "7", "7", "1" },
                { "!=", "7", "3", "1" }
        };
        for (String[] op : ops) {
            cases.add(new TestCase(formatName(index++), "comparacao " + op[0],
                    "int a = " + op[1] + ";\nint b = " + op[2] + ";\nif (a " + op[0]
                            + " b) {\n    System.out.println(" + op[3] + ");\n}"));
        }
        return index;
    }

    private static int addLogicalTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "and verdadeiro",
                "int a = 2;\nint b = 3;\nif (a > 0 && b > 0) {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "and falso",
                "int a = 2;\nint b = 0;\nif (a > 0 && b > 0) {\n    System.out.println(0);\n}\nSystem.out.println(1);"));
        cases.add(new TestCase(formatName(index++), "or verdadeiro",
                "int a = 0;\nint b = 2;\nif (a > 0 || b > 0) {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "or falso",
                "int a = 0;\nint b = 0;\nif (a > 0 || b > 0) {\n    System.out.println(0);\n}\nSystem.out.println(1);"));
        cases.add(new TestCase(formatName(index++), "not simples",
                "int x = 1;\nif (!(x < 0)) {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "not com parenteses",
                "int x = 5;\nif (!(x == 0)) {\n    System.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "logico composto",
                "int a = 1;\nint b = 2;\nint c = 3;\nif (a < b && b < c || a == 1) {\n    System.out.println(1);\n}"));
        return index;
    }

    private static int addWhileTests(List<TestCase> cases, int index) {
        for (int limit = 1; limit <= 5; limit++) {
            cases.add(new TestCase(formatName(index++), "while soma ate " + limit,
                    "int i = 0;\nint sum = 0;\nwhile (i < " + limit + ") {\n    sum = sum + i;\n    i = i + 1;\n}\nSystem.out.println(sum);"));
        }
        return index;
    }

    private static int addForTests(List<TestCase> cases, int index) {
        for (int n = 1; n <= 4; n++) {
            cases.add(new TestCase(formatName(index++), "for crescente " + n,
                    "int i;\nfor (i = 0; i < " + n + "; i = i + 1) {\n    System.out.println(i);\n}"));
        }
        cases.add(new TestCase(formatName(index++), "for com i++",
                "int i;\nfor (i = 0; i < 3; i++) {\n    System.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for <= limite",
                "int i;\nfor (i = 0; i <= 2; i = i + 1) {\n    System.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for decrescente",
                "int i;\nfor (i = 3; i >= 0; i = i - 1) {\n    System.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for decrescente com --",
                "int i;\nfor (i = 2; i >= 0; i--) {\n    System.out.println(i);\n}"));
        return index;
    }

    private static int addSwitchTests(List<TestCase> cases, int index) {
        for (int value = 0; value < 5; value++) {
            cases.add(new TestCase(formatName(index++), "switch case " + value,
                    "int x = " + value + ";\nswitch (x) {\n    case 0:\n        System.out.println(10);\n        break;\n    case 1:\n        System.out.println(11);\n        break;\n    default:\n        System.out.println(99);\n}"));
        }
        return index;
    }

    private static int addIncrementTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "incremento pos fixo",
                "int x = 1;\nx++;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "decremento pos fixo",
                "int x = 3;\nx--;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "incremento em loop manual",
                "int x = 0;\nint i;\nfor (i = 0; i < 3; i++) {\n    x++;\n}\nSystem.out.println(x);"));
        return index;
    }

    private static int addCommentTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "comentario linha",
                "// comentario\nint x = 4;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "comentario bloco",
                "/* bloco */\nint x = 6;\nSystem.out.println(x);"));
        return index;
    }

    private static int addComplexTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "mix if while print",
                "int x = 1;\nwhile (x < 4) {\n    if (x > 0) {\n        System.out.println(x);\n    }\n    x = x + 1;\n}"));
        cases.add(new TestCase(formatName(index++), "mix for acumulador",
                "int sum = 0;\nint i;\nfor (i = 1; i <= 4; i = i + 1) {\n    sum = sum + i;\n}\nSystem.out.println(sum);"));
        cases.add(new TestCase(formatName(index++), "mix switch e loop",
                "int x = 2;\nint y = 0;\nswitch (x) {\n    case 2:\n        y = 10;\n        break;\n    default:\n        y = 0;\n}\nSystem.out.println(y);"));
        cases.add(new TestCase(formatName(index++), "mix expressao aninhada",
                "int a = 2;\nint b = 3;\nint c = 0;\nc = a + b;\nc = c * 2;\nSystem.out.println(c);"));
        cases.add(new TestCase(formatName(index++), "mix boolean e if",
                "boolean ok = true;\nboolean other = true;\nif (ok == other) {\n    System.out.println(1);\n} else {\n    System.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "mix string e int",
                "String msg = \"val=8\";\nSystem.out.println(msg);"));
        cases.add(new TestCase(formatName(index++), "mix negacao e comparacao",
                "int x = 4;\nif (x != 0) {\n    if (x < 10) {\n        System.out.println(1);\n    }\n}"));
        cases.add(new TestCase(formatName(index++), "mix for decremento acumulado",
                "int total = 0;\nint i;\nfor (i = 3; i >= 1; i = i - 1) {\n    total = total + i;\n}\nSystem.out.println(total);"));
        return index;
    }

    private static int addBooleanTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "boolean print true e false",
                "boolean t = true;\nboolean f = false;\nSystem.out.println(t);\nSystem.out.println(f);"));
        cases.add(new TestCase(formatName(index++), "boolean atribuicao",
                "boolean flag = false;\nSystem.out.println(flag);\nflag = true;\nSystem.out.println(flag);"));
        cases.add(new TestCase(formatName(index++), "boolean igualdade",
                "boolean a = true;\nboolean b = true;\nif (a == b) {\n    System.out.println(1);\n} else {\n    System.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "boolean desigualdade",
                "boolean a = true;\nboolean b = false;\nif (a != b) {\n    System.out.println(1);\n} else {\n    System.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "boolean and or entre variaveis",
                "boolean a = true;\nboolean b = true;\nboolean c = false;\nif (a == b && c != b) {\n    System.out.println(1);\n} else {\n    System.out.println(0);\n}"));
        return index;
    }

    private static int addBehaviorTests(List<TestCase> cases, int index) {
        cases.add(new TestCase(formatName(index++), "println constante 42",
                "System.out.println(42);"));
        cases.add(new TestCase(formatName(index++), "println zero",
                "System.out.println(0);"));
        cases.add(new TestCase(formatName(index++), "declara e atribui int",
                "int x;\nx = 7;\nSystem.out.println(x);"));
        cases.add(new TestCase(formatName(index++), "tres println sequenciais",
                "System.out.println(1);\nSystem.out.println(2);\nSystem.out.println(3);"));
        cases.add(new TestCase(formatName(index++), "soma de duas variaveis",
                "int a, b;\na = 2;\nb = 3;\nSystem.out.println(a + b);"));
        cases.add(new TestCase(formatName(index++), "subtracao de variaveis",
                "int a, b;\na = 10;\nb = 4;\nSystem.out.println(a - b);"));
        cases.add(new TestCase(formatName(index++), "multiplicacao de variaveis",
                "int a, b;\na = 6;\nb = 7;\nSystem.out.println(a * b);"));
        cases.add(new TestCase(formatName(index++), "divisao exata por 5",
                "int x, c;\nx = 10;\nc = x / 5;\nSystem.out.println(c);"));
        cases.add(new TestCase(formatName(index++), "divisao inteira por 3",
                "int x, c;\nx = 10;\nc = x / 3;\nSystem.out.println(c);"));
        cases.add(new TestCase(formatName(index++), "resto da divisao",
                "int x, r;\nx = 10;\nr = x % 3;\nSystem.out.println(r);"));
        cases.add(new TestCase(formatName(index++), "precedencia sem parenteses",
                "int r;\nr = 2 + 3 * 4;\nSystem.out.println(r);"));
        cases.add(new TestCase(formatName(index++), "precedencia com parenteses",
                "int r;\nr = (2 + 3) * 4;\nSystem.out.println(r);"));
        cases.add(new TestCase(formatName(index++), "expressao composta com divisao",
                "int x, a, b, c;\nx = 10;\nb = x * 5;\nc = x / 5;\na = b + c;\nSystem.out.println(a);"));
        cases.add(new TestCase(formatName(index++), "if verdadeiro simples",
                "int x;\nx = 10;\nif (x > 5) {\nSystem.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "if falso sem else",
                "int x;\nx = 1;\nif (x > 5) {\nSystem.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "if falso com else",
                "int x;\nx = 1;\nif (x > 5) {\nSystem.out.println(1);\n} else {\nSystem.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "if verdadeiro com else",
                "int x;\nx = 10;\nif (x > 5) {\nSystem.out.println(1);\n} else {\nSystem.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "if com maior ou igual",
                "int x;\nx = 5;\nif (x >= 5) {\nSystem.out.println(9);\n}"));
        cases.add(new TestCase(formatName(index++), "if com igualdade",
                "int x;\nx = 7;\nif (x == 7) {\nSystem.out.println(7);\n}"));
        cases.add(new TestCase(formatName(index++), "if com diferente de zero",
                "int x;\nx = 7;\nif (x != 0) {\nSystem.out.println(1);\n}"));
        cases.add(new TestCase(formatName(index++), "if encadeado aninhado",
                "int x, y;\nx = 10;\ny = 2;\nif (x > 5) {\nif (y < 5) {\nSystem.out.println(y);\n}\n}"));
        cases.add(new TestCase(formatName(index++), "while imprime 0 a 2",
                "int i;\ni = 0;\nwhile (i < 3) {\nSystem.out.println(i);\ni = i + 1;\n}"));
        cases.add(new TestCase(formatName(index++), "while condicao falsa",
                "int i;\ni = 5;\nwhile (i < 3) {\nSystem.out.println(i);\ni = i + 1;\n}"));
        cases.add(new TestCase(formatName(index++), "while decrescente",
                "int i;\ni = 3;\nwhile (i >= 1) {\nSystem.out.println(i);\ni = i - 1;\n}"));
        cases.add(new TestCase(formatName(index++), "while acumula soma",
                "int i, s;\ni = 0;\ns = 0;\nwhile (i < 4) {\ns = s + i;\ni = i + 1;\n}\nSystem.out.println(s);"));
        cases.add(new TestCase(formatName(index++), "for imprime 0 a 4",
                "int i;\nfor (i = 0; i < 5; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for com limite inclusivo",
                "int i;\nfor (i = 0; i <= 5; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for com limite variavel",
                "int i, x;\nx = 3;\nfor (i = 0; i <= x; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for com incremento de 2",
                "int i;\nfor (i = 0; i < 10; i = i + 2) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for com inicio em 2",
                "int i;\nfor (i = 2; i < 6; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for sem iteracao",
                "int i;\nfor (i = 0; i < 0; i = i + 1) {\nSystem.out.println(i);\n}\nSystem.out.println(99);"));
        cases.add(new TestCase(formatName(index++), "for com limite em variavel n",
                "int i, n;\nn = 4;\nfor (i = 0; i < n; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for crescente ate x inclusive",
                "int i, x;\nx = 4;\nfor (i = 0; i <= x; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "for com limite x + 1",
                "int i, x;\nx = 3;\nfor (i = 0; i < x + 1; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "dois for sequenciais",
                "int i, j;\nfor (i = 0; i < 2; i = i + 1) {\nSystem.out.println(i);\n}\nfor (j = 0; j < 2; j = j + 1) {\nSystem.out.println(j);\n}"));
        cases.add(new TestCase(formatName(index++), "switch um case",
                "int x;\nx = 1;\nswitch (x) {\ncase 1:\nSystem.out.println(10);\nbreak;\n}"));
        cases.add(new TestCase(formatName(index++), "switch dois cases",
                "int x;\nx = 2;\nswitch (x) {\ncase 1:\nSystem.out.println(1);\nbreak;\ncase 2:\nSystem.out.println(2);\nbreak;\n}"));
        cases.add(new TestCase(formatName(index++), "switch com default",
                "int x;\nx = 9;\nswitch (x) {\ncase 1:\nSystem.out.println(1);\nbreak;\ndefault:\nSystem.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "switch case zero",
                "int x;\nx = 0;\nswitch (x) {\ncase 0:\nSystem.out.println(0);\nbreak;\n}"));
        cases.add(new TestCase(formatName(index++), "switch tres cases",
                "int x;\nx = 3;\nswitch (x) {\ncase 1:\nSystem.out.println(1);\nbreak;\ncase 2:\nSystem.out.println(2);\nbreak;\ncase 3:\nSystem.out.println(3);\nbreak;\n}"));
        cases.add(new TestCase(formatName(index++), "switch e println apos",
                "int x;\nx = 1;\nswitch (x) {\ncase 1:\nSystem.out.println(1);\nbreak;\n}\nSystem.out.println(9);"));
        cases.add(new TestCase(formatName(index++), "for acumula soma 1 a 4",
                "int i, s;\ns = 0;\nfor (i = 1; i < 5; i = i + 1) {\ns = s + i;\n}\nSystem.out.println(s);"));
        cases.add(new TestCase(formatName(index++), "for calcula fatorial",
                "int i, n, f;\nn = 5;\nf = 1;\nfor (i = 1; i <= n; i = i + 1) {\nf = f * i;\n}\nSystem.out.println(f);"));
        cases.add(new TestCase(formatName(index++), "expressao, if e for combinados",
                "int x, a, b, c, i;\nx = 10;\nb = x * 5;\nc = x / 5;\na = b + c;\nif (x > 5) {\nSystem.out.println(x);\n}\nfor (i = 0; i <= x; i = i + 1) {\nSystem.out.println(i);\n}"));
        cases.add(new TestCase(formatName(index++), "while com if interno",
                "int i;\ni = 0;\nwhile (i < 5) {\nif (i == 2) {\nSystem.out.println(99);\n}\ni = i + 1;\n}"));
        cases.add(new TestCase(formatName(index++), "for com switch aninhado",
                "int i, x;\nfor (i = 0; i < 3; i = i + 1) {\nx = i;\nswitch (x) {\ncase 0:\nSystem.out.println(0);\nbreak;\ncase 1:\nSystem.out.println(1);\nbreak;\ndefault:\nSystem.out.println(9);\n}\n}"));
        cases.add(new TestCase(formatName(index++), "if com for interno",
                "int i, x;\nx = 2;\nif (x > 1) {\nfor (i = 0; i < 2; i = i + 1) {\nSystem.out.println(i);\n}\n} else {\nSystem.out.println(0);\n}"));
        cases.add(new TestCase(formatName(index++), "media aritmetica inteira",
                "int a, b, s;\na = 7;\nb = 4;\ns = (a + b) / 2;\nSystem.out.println(s);"));
        cases.add(new TestCase(formatName(index++), "while decrescente 3 a 1",
                "int i;\ni = 3;\nwhile (i > 0) {\nSystem.out.println(i);\ni = i - 1;\n}"));
        cases.add(new TestCase(formatName(index++), "dois switch sequenciais",
                "int a, b;\na = 1;\nb = 2;\nswitch (a) {\ncase 1:\nSystem.out.println(10);\nbreak;\n}\nswitch (b) {\ncase 2:\nSystem.out.println(20);\nbreak;\n}"));
        cases.add(new TestCase(formatName(index++), "main vazio",
                ""));
        cases.add(new TestCase(formatName(index++), "declara variaveis sem saida",
                "int x, y;\nx = 1;\ny = 2;"));
        cases.add(new TestCase(formatName(index++), "println numero grande",
                "System.out.println(99999);"));
        cases.add(new TestCase(formatName(index++), "for com uma iteracao",
                "int i;\nfor (i = 0; i < 1; i = i + 1) {\nSystem.out.println(7);\n}"));
        cases.add(new TestCase(formatName(index++), "if falso e println depois",
                "int x;\nx = 0;\nif (x > 0) {\nSystem.out.println(1);\n}\nSystem.out.println(2);"));
        cases.add(new TestCase(formatName(index++), "identificador com underscore",
                "int _v;\n_v = 8;\nSystem.out.println(_v);"));
        cases.add(new TestCase(formatName(index++), "tres variaveis impressas",
                "int a, b, c;\na = 1;\nb = 2;\nc = 3;\nSystem.out.println(a);\nSystem.out.println(b);\nSystem.out.println(c);"));
        cases.add(new TestCase(formatName(index++), "soma encadeada de constantes",
                "int r;\nr = 1 + 2 + 3 + 4;\nSystem.out.println(r);"));
        cases.add(new TestCase(formatName(index++), "while executa uma vez",
                "int i;\ni = 0;\nwhile (i == 0) {\nSystem.out.println(5);\ni = 1;\n}"));
        cases.add(new TestCase(formatName(index++), "atribuicao e println",
                "int x;\nx = 42;\nSystem.out.println(x);"));
        return index;
    }

    private static String formatName(int index) {
        return String.format("Test%03d", index);
    }
}
