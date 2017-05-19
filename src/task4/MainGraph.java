package task4;

/**
 * Created by Galushkin Pavel on 19.05.2017.
 */

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class MainGraph {
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
                    Arrays.sort(arr);           //Sort ascending x,y
                    arr = sortByDirection(arr); //Sort by angle relative to first point
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
    private static double[] fillArray(int size, int dim) {
        double[] arr = new double[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Math.floor(Math.random() * dim + 1) + (Math.floor(Math.random() * dim + 1) / 100);
        }
        return arr;
    }

    // Return true if B is righter than C relative to first
    private static boolean righter(double first, double B, double C) {
        int fX, fY, bX, bY, cX, cY;
        fX = (int) first;
        bX = (int) B;
        cX = (int) C;
        fY = (int) ((first - ((int) first)) * 100);
        bY = (int) ((B - ((int) B)) * 100);
        cY = (int) ((C - ((int) C)) * 100);
        if ((bX - fX) * (cY - bY) - (bY - fY) * (cX - bX) < 0) {
            return true;
        } else {
            return false;
        }
    }

    // Sort points counterclockwise relative to one
    private static double[] sortByDirection(double[] arr) {
        // find point with min x coord
        for (int i = 1; i < arr.length; i++) {
            if (arr[0] > arr[i]) {
                double tmp = arr[0];
                arr[0] = arr[i];
                arr[i] = tmp;
            }
        }
        // sort rest of points
        for (int i = 2; i < arr.length; i++) {
            int j = i;
            while (j > 1 && righter(arr[0], arr[j - 1], arr[j])) {
                double tmp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = tmp;
                j--;
            }
        }
        return arr;
    }

    // Convert array to printable view
    private static String arrToString(double[] arr) {
        StringBuilder sb = new StringBuilder("[");
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
