package task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for calculation of a square root
 */
public class Main {

    /**
     * Input number for calculation of a square root in range from 0 to {@code Integer.MAX_VALUE}
     *
     * @return number for calculation of a square root
     */
    private static int getNumber() {
        int num = 0;
        boolean b = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (b) {
                System.out.printf("Input number (0 <= number <= %d): ", Integer.MAX_VALUE);
                try {
                    num = Integer.parseInt(br.readLine());
                    b = num < 0 || num > Integer.MAX_VALUE;
                    if (b) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Illegal number format. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return num;
    }

    /**
     * Evaluation of a square root of {@code double} value.
     * @param x incoming value, double
     * @return int value of a square root
     */
    private static int my_sqrt(double x) {
        double S = x, a = 1, b = x, EPS = 1e-10;
        while (Math.abs(a - b) > EPS) {
            a = (a + b) / 2;
            b = S / a;
        }
        return (int)((a + b) / 2);
    }

    // Newton’s Method
    // Hacker’s Delight. Second Edition. ISBN-13: 978-0-321-84268-8, Chapter 11

    /**
     * Newton’s Method for calculation of a square root</br>
     * Hacker’s Delight. Second Edition. ISBN-13: 978-0-321-84268-8, Chapter 11</br>
     * @param x incoming value, int
     * @return int value of a square root
     */
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

    /**
     * A Hardware Algorithm for calculation of a square root</br>
     * Hacker’s Delight. Second Edition. ISBN-13: 978-0-321-84268-8, Chapter 11</br>
     * @param x incoming value, int
     * @return int value of a square root
     */
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
        int n = getNumber();

        //System.out.println(String.format("sqrt = %d", isqrt1(n)));
        //System.out.println(String.format("sqrt = %d", isqrt2(n)));
        System.out.println(String.format("sqrt = %d", my_sqrt(n)));
    }
}
