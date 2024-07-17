import java.util.Arrays;

public class Sort {
    public static void main(String[] args) {
        int[] nums = new int[] { 5, 4, 3, 2, 1, -1, 72 };
        bubbleSort(nums);
        System.out.println("-------");
        bubbleSortPrimeagen(nums);

        //System.out.println(Arrays.toString(bubbleSort(nums)));
        //System.out.println(Arrays.toString(bubbleSortPrimeagen(nums)));
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

        // [0, n - 2]
        for (int i = 0; i<nums.length - 1; i++) {
            // n - i - 1 to account for shifting last unsorted index and 0 based indexing (n - i causes IndexOutOfBoundException)
            for (int j = 0; j<nums.length - 1 - i; j++) {
                // if the number that comes before is larger, swap
                if (nums[j] > nums[j + 1]) {
                    swap(nums, j, j + 1);
                }
            }
        }

        return nums;
    }

    /**
     * In class implementation
     * 
     * Time Complexity: O(n^2)
     * 
     * Note: i<nums.length - 1 would also work for outer loop. 
     * This is because, when i = n - 1, j = 0. 
     * Because only 1 value can be at index 0 and a list of size 1 is always sorted, we can actually stop one outer loop iteration early when i = n - 2
     */
    private static int[] bubbleSortPrimeagen(int[] nums) {
        // [0, n - 1] == [0, n)
        for (int i=0; i<nums.length; i++) {
            // [0, n - 2] --> inclusive range == [0, n - 1) --> half open range
            for (int j=0; j<nums.length - 1 - i; j++) {
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