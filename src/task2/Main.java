package task2;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 19.05.2017.
 */
public class Main {
    private static int HEAPSIZE;
    private static int[] H;
    //private static List<String> rotateList = new ArrayList<>();

    public static void siftDown(int i) {
        while (true) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            int maxIndex = i;

            if (leftChild < HEAPSIZE && H[leftChild] > H[maxIndex])
                maxIndex = leftChild;

            if (rightChild < HEAPSIZE && H[rightChild] > H[maxIndex])
                maxIndex = rightChild;

            if (maxIndex == i) return;
            //rotateList.add(i + " " + maxIndex);
            int temp = H[i];
            H[i] = H[maxIndex];
            H[maxIndex] = temp;
            i = maxIndex;
        }
    }

    public static int getMax() {

        int max = H[0];
        H[0] = H[HEAPSIZE - 1];
        HEAPSIZE--;
        siftDown(0);
        return max;
    }

    public static void heapSort() {
        for (int i = HEAPSIZE - 1; i >= 0; i--) {
            H[i] = getMax();
            siftDown(0);
        }
    }

    public static void main(String[] args) {
        HEAPSIZE = 20;
        int low = 0, high = 100;
        int k = 4;
        H = new Random().ints(HEAPSIZE, low, high).toArray();
        // Arrays.sort(arr); не используем потому что..
        // PriorityQueue не используем потому что нет доступа по индексу, и при большом k придется перебирать все значения
        System.out.println(Arrays.stream(H)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));

        // make a heap from array
        for (int i = HEAPSIZE / 2; i >= 0; i--) {
            siftDown(i);
        }
        System.out.println(Arrays.stream(H)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
        heapSort();
        System.out.println(Arrays.stream(H)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
        System.out.println("Answer: array[k] = " + H[k-1]);
    }
}
