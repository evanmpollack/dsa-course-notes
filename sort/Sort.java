import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] nums = new int[] { 5, 4, 3, 2, 1 };

        System.out.println(Arrays.toString(bubbleSort(nums)));
    }

    /**
     * Personal implementation
     * 
     * Time Complexity: O(n^2)
     */
    private static int[] bubbleSort(int[] nums) {
        // Steps:
        // 1. Use two loops, i and j
        // 2. Keep track of last unsorted index with outer loop (i)
        // 3. Go through a[j] and a[j + 1] until last sorted index until last sorted index == 0;

        // n - 1 to account for 0-based indexing
        int lengthZeroBased = nums.length - 1;
        for (int i = 0; i<lengthZeroBased; i++) {
            // n - i - 1 to account for shifting last unsorted index and 0 based indexing (n - i causes IndexOutOfBoundException)
            int lastSortedIndex = lengthZeroBased - i;
            for (int j = 0; j<lastSortedIndex; j++) {
                // if the number that comes before is larger, swap
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }

        return nums;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}