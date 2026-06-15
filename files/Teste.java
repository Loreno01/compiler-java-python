public class Teste {
    public static void main(String[] args) {
        int x, a, b, c, i;
        double f;
        boolean condition;
        String name;

        name = "Fulano";
        
        x = 10;

        b = x * 5;
        c = x / 5;

        a = b + c;

        if (x > 5) {
            System.out.println(x);
        }
        
        for (i = 0; i <= x; i = i + 1) {
            System.out.println(i);
        }
    }
}