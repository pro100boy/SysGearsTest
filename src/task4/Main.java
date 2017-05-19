package task4;

/**
 * Created by Galushkin Pavel on 19.05.2017.
 */

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final int DIM = 20;         //Field dimension
        int arrSize;                //Num of elements
        Scanner sc = new Scanner(System.in);
        System.out.print("Input qty of points >2: ");
        while (sc.hasNext()) {
            if (sc.hasNextInt()) {
                arrSize = sc.nextInt();
                if (arrSize > 1) {
                    double[] arr = fillArray(arrSize, DIM);
                    System.out.println("Random array: " + (arrToString(arr)));
                    Arrays.sort(arr);
                    System.out.println("Ordered array for drawing: " + (arrToString(arr)));
                    System.out.println();
                    DrawGraph.createAndShowGui(arr, DIM);
                } else {
                    System.out.println("Wrong input");
                }
                sc.nextLine();
            } else {
                System.out.println("Wrong input");
                sc.nextLine();
            }
            System.out.print("Input qty of points >2: ");
        }
    }

    //   Return array of n-elements with random coordinates x,y
    public static double[] fillArray(int size, int dim) {
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Math.floor(Math.random() * dim + 1) + (Math.floor(Math.random() * dim + 1) / 100);
        }
        return arr;
    }

    public static String arrToString(double[] arr) {
        StringBuffer sb = new StringBuffer("[");
        String pref = "";
        for (double db : arr) {
            sb.append(pref);
            pref = "; ";
            sb.append((int) db);
            sb.append(",");
            sb.append((int) ((db - ((int) db)) * 100));
        }
        sb.append("]");
        return sb.toString();
    }
}
