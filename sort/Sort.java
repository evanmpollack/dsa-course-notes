import java.util.Arrays;
import java.util.Random;

public class Sort {
    public static void main(String[] args) {
        int[] nums = new int[10];
        Random r = new Random();
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100) + 1;
        }

        // bubbleSort(nums);
        // System.out.println("-------");
        // bubbleSortPrimeagen(nums);
        // System.out.println("-------");
        quickSortMedianIndex(nums, 0, nums.length);
        System.out.println(Arrays.toString(nums));
        // System.out.println(Arrays.toString(bubbleSort(nums)));
        // System.out.println(Arrays.toString(bubbleSortPrimeagen(nums)));
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

	// Personal Implementation using last index as pivot
    private static void quickSort(int[] arr, int start, int end) {
    	/**
    	* 1. Choose pivot and move to end (already done in this version)
    	* 2. Partition elements around pivot
    	* 3. Call quickSort on left portion
    	* 4. Call quickSort on right portion
    	*/

		int length = end - start;
    	if (arr == null || length <= 1) {
    		return;
    	}
    	
    	int resultingPivotIndex = lomutoPartition(arr, start, end - 1);
		// left portion
    	quickSort(arr, start, resultingPivotIndex);
    	// right portion
    	quickSort(arr, resultingPivotIndex + 1, end);
    }

    // Personal implementation using non-end pivot
    private static void quickSortMedianIndex(int[] arr, int start, int end) {
        int length = end - start;
        if (arr == null || length <= 1) {
            return;
        }
        // When pivot isn't already at end, you should swap it with the last element to get it out of the way
        // Make sure median is offset using start to ensure it is the median index of the correct range
        int medianIndex = (length / 2) + start;
        swap(arr, medianIndex, end - 1);
        int resultingPivotIndex = hoarePartition(arr, start, end - 1);
        quickSortMedianIndex(arr, start, resultingPivotIndex);
        quickSortMedianIndex(arr, resultingPivotIndex + 1, end);
    }

    /**
     * Implementation of Hoare's partition algorithm.
     * 
     * Places two pointers, one at the start and one at the second to last index (exclude's pivot).
     * While the two pointers, l and r, haven't crossed,
     * - Swap l with r if l > pivot and r < pivot (inversion detected)
     * - Increment l if l <= pivot
     * - Decrement r if r > pivot
     * Once the l and r pointers cross, swap pivot with l, as l will be at the first index grester than the pivot
     * 
     * Returns the final position of the pivot (aka lo)
     */
    private static int hoarePartition(int[] arr, int start, int end) {
		/**
		* 1. Get value at pivot index
		* 2. While lo <= hi
		*	- Swap if lo is greater than pivot and hi is less than pivot
		*	- Increment lo if lo is less than pivot
		*	- Decrement hi if hi is greater than pivot
		* 3. Swap pivot with lo (left) pointer
		* 4. Return lo (the new index of pivot)
		*/

    	int lo = start;
        // exclude pivot index
    	int hi = end - 1;
    	int pivot = arr[end];
    	while (lo <= hi) {
            if (arr[lo] > pivot && arr[hi] < pivot) {
    			swap(arr, lo, hi);
    		}

    		if (arr[lo] <= pivot) {
    			lo++;
    		}

    		if (arr[hi] > pivot) {
    			hi--;
    		}
    	}
    	swap(arr, lo, end);
    	return lo;
    }



    /**
     * Implementation of Lomuto's partition.
     * 
     * Places two pointers at the beginning of the range, 
     * with one walking the range and the other keeping track
     * of the first index greater than the pivot. Anytime i crosses a value less than pivot,
     * the two pointers swap and the one keeping track of the first index > pivot is incremented.
     * After iteration, swap pivot with first index greater than pivot
     * 
     * Note: Some implementations set lo at start - 1 instead of start because they are using the pseudocode that leverages do-while loops.
     * When using do-while loops, starting at start - 1 is more intuitive
     * 
     * Returns the final position of the pivot (aka the pivot that keeps track of the first index > pivot)
     * 
     * IN CLASS VARIATION
     */
    private static int lomutoPartition(int[] arr, int start, int end) {
        int lo = start;
        int pivot = arr[end];
        for (int i = lo; i < end; i++) {
            if (arr[i] <= pivot) {
                swap(arr, i, lo);
                lo++;
            }
        }
        swap(arr, lo, end);
        return lo;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}