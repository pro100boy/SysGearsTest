package task1;

public class Main {

    static double EPS = 1e-10;

    private static double my_sqrt(double x) {
        double S = x, a = 1, b = x;
        while (Math.abs(a - b) > EPS) {
            a = (a + b) / 2;
            b = S / a;
        }
        return (a + b) / 2;
    }

    private static int isqrt1(int x) {
        int x1;
        int s, g0, g1;

        if (x <= 1) return x;
        s = 1;
        x1 = x - 1;
        if (x1 > 65535) {
            s = s + 8;
            x1 = x1 >> 16;
        }
        if (x1 > 255) {
            s = s + 4;
            x1 = x1 >> 8;
        }
        if (x1 > 15) {
            s = s + 2;
            x1 = x1 >> 4;
        }
        if (x1 > 3) {
            s = s + 1;
        }

        g0 = 1 << s;                // g0 = 2**s.
        g1 = (g0 + (x >> s)) >> 1;  // g1 = (g0 + x/g0)/2.

        while (g1 < g0) {           // Do while approximations
            g0 = g1;                 // strictly decrease.
            g1 = (g0 + (x / g0)) >> 1;
        }
        return g0;
    }

    // Hardware algorithm [GLS]
    private static int isqrt2(int x) {
        int m, y, b;

        m = 0x40000000;
        y = 0;
        while (m != 0) {              // Do 16 times.
            b = y | m;
            y = y >> 1;
            if (x >= b) {
                x = x - b;
                y = y | m;
            }
            m = m >> 2;
        }
        return y;
    }

    public static void main(String[] args) {
        int n = 28;

        System.out.println("isqrt1:");
        for (int i = 0; i <= n; i++)
            System.out.println(String.format("i = %d, sqrt = %d", i, isqrt1(i)));

        System.out.println("\nisqrt2:");
        for (int i = 0; i <= n; i++)
            System.out.println(String.format("i = %d, sqrt = %d", i, isqrt2(i)));

        System.out.println("\nmy_sqrt:");
        for (int i = 0; i <= n; i++)
            System.out.println(String.format("i = %d, sqrt = %f", i, my_sqrt(i)));

    }
}
