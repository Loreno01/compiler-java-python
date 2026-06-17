public class Teste {
    public static void main(String[] args) {
        int x, a, b, c, i, count;
        double f;
        boolean condition;
        boolean condition2;
        String name;
        int test = 2 * 10;

        x = 10;
        name = "FuLaNo";
        System.out.println(name);
        System.out.println("\'Teste aspas simples\'");

        condition = true;
        condition2 = false;

        b = x * 5;
        c = x / 5;
        a = (b + c) % 7;
        f = x / 5;
        System.out.println(a);
        System.out.println(f);

        if (x > 2)

        {
            x = x + 1;
            System.out.println(x);
        } else {
            System.out.println(0);
        }

        if (x < 2) {
            System.out.println(1);
        }

        if (b != c) {
            System.out.println("\'b\' é diferente de \'c\'");
        }

        while (x > 7) {
            System.out.println(x);
            x = x - 1;
        }

        for (i = 0; i <= x; i = i + 1) {
            System.out.println(i);
        }

        for (i = 0; i < 3; i++) {
            System.out.println(i);
        }

        for (i = 3; i >= 0; i--) {
            System.out.println(i);
        }

        count = 12;
        switch (count) {
            case 10:
                System.out.println(10);
                break;
            case 12:
                System.out.println(12);
                break;
            default:
                System.out.println(0);
        }
    }
}
