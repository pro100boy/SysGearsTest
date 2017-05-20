package task2;

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

    public static void main(String[] args) {
        int arraySize = 20;
        int low = 0, high = 100;
        int k = 4;
        int[] arr = new Random().ints(arraySize, low, high).toArray();

        /**
         * Sorts the specified array into ascending numerical order.
         *
         * <p>Implementation note: The sorting algorithm is a Dual-Pivot Quicksort
         * by Vladimir Yaroslavskiy, Jon Bentley, and Joshua Bloch. This algorithm
         * offers O(n log(n)) performance on many data sets that cause other
         * quicksorts to degrade to quadratic performance, and is typically
         * faster than traditional (one-pivot) Quicksort implementations.
         *
         * @param a the array to be sorted
         */
        if (k > 100_000)
            Arrays.sort(arr);
        else {
            BinaryHeap binaryHeap = new Main().new BinaryHeap(arr);
            // sort array. O(N log N)
            arr = binaryHeap.heapSort();
        }

        // PriorityQueue doesn't approach since doesn't provide access to elements by index

        // print unsorted array
        //printArray(arr);


        // print sorted array (for testing result)
        printArray(arr);

        System.out.println("Answer: array[k] = " + arr[k - 1]);
    }
}
