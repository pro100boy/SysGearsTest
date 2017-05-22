package task2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Galushkin Pavel on 19.05.2017.
 */
public class Main {
    class BinaryHeap {
        private final int[] heap;
        private int heapsize;

        public BinaryHeap(int[] heap) {
            this.heap = heap;
            this.heapsize = heap.length;

            // Building a heap from an array O(log N)
            for (int i = heapsize / 2; i >= 0; i--) {
                siftDown(i);
            }
        }

        private void siftDown(int i) {
            while (true) {
                int leftChild = 2 * i + 1;
                int rightChild = 2 * i + 2;
                int maxIndex = i;

                if (leftChild < heapsize && heap[leftChild] > heap[maxIndex])
                    maxIndex = leftChild;

                if (rightChild < heapsize && heap[rightChild] > heap[maxIndex])
                    maxIndex = rightChild;

                if (maxIndex == i) return;

                int temp = heap[i];
                heap[i] = heap[maxIndex];
                heap[maxIndex] = temp;
                i = maxIndex;
            }
        }

        private int getMax() {
            int max = heap[0];
            heap[0] = heap[heapsize - 1];
            heapsize--;
            siftDown(0);
            return max;
        }

        // sort array without extra memory
        public int[] heapSort() {
            for (int i = heapsize - 1; i > 0; i--) {
                heap[i] = getMax();
                siftDown(0);
            }
            return heap;
        }
    }

    private static void printArray(int[] arr) {
        System.out.println(Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
    }

    private static int getIdx(int arraySize) {
        int idx = 0;
        boolean b = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (b) {
                System.out.printf("Input index of array cell (1 <= index <= %d): ", arraySize);
                try {
                    idx = Integer.parseInt(br.readLine());
                    b = idx < 1 || idx > arraySize;
                    if (b) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Illegal number format. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idx;
    }

    public static void main(String[] args) {
        // set random array size (up to 200000 cells)
        int arraySize = 1;
        while (arraySize <= 1)
            arraySize = new Random().nextInt(200_000);

        // input index of the defined element
        int k = getIdx(arraySize);

        // bounds of array values: from low (inclusive) to high (exclusive)
        int
                low = 0,
                high = 1_000;

        // initialize array
        int[] arr = new Random().ints(arraySize, low, high).toArray();

        // print unsorted array
        // printArray(arr);

        // start benchmark
        long start = System.nanoTime();

        /**
         * PriorityQueue doesn't approach since doesn't provide access to elements by index
         *
         * Review and testing sort methods
         * http://info.javarush.ru/EvIv/2014/11/28/%D0%9E%D0%B1%D0%B7%D0%BE%D1%80-%D0%B8-%D1%82%D0%B5%D1%81%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5-%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%BE%D0%B2-%D1%81%D0%BE%D1%80%D1%82%D0%B8%D1%80%D0%BE%D0%B2%D0%BA%D0%B8-%D0%A7%D0%B0%D1%81%D1%82%D1%8C-I.html
         */
        if (arraySize > 100_000)
            Arrays.sort(arr);
        else {
            BinaryHeap binaryHeap = new Main().new BinaryHeap(arr);
            /**
             * https://betterexplained.com/articles/sorting-algorithms/#Heapsort_BestAvgWorst_ON_lg_N
             * Heapsort has O(N lgN) behavior, even in the worst case, making it good for real-time applications
             */
            arr = binaryHeap.heapSort();
        }

        //System.out.println("Время работы: " + (System.nanoTime() - start));
        // print sorted array (for testing result)
        // printArray(arr);

        System.out.printf("Answer: array[%d] = %d", k, arr[k - 1]);
    }
}
