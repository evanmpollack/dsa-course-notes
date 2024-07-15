public class Search {
    public static void main(String[] args) {
        int[] arr = new int[] { 3, 4, 23, 67, 1, 2 };
        int[] sortedArr = new int[] { 1, 2, 3, 4, 5 };
        boolean[] buildingFloors = new boolean[] { true, true };

        // System.out.println(linearSearch(arr, 24));

        // System.out.println(binarySearch(sortedArr, 0));

        // System.out.println(binarySearchRecursive(sortedArr, 0, 0, sortedArr.length - 1));

        // System.out.println(twoCrystalBalls(buildingFloors));
        System.out.println(twoCrystalBallsSqrt(buildingFloors));
    }

    // Time Complexity: O(n)
    private static boolean linearSearch(int[] arr, int target) {
        boolean found = false;
        for (int i=0; i<arr.length; i++) {
            if (arr[i] == target) {
                found = true;
                break;
            }
        }
        return found;
    }

    // Time Complexity: O(log(n))
    private static boolean binarySearch(int[] arr, int target) {
        boolean found = false;
        int lo = 0;
        int hi = arr.length - 1;
        
        while (lo <= hi) {
            // Calculate the mid like this because (lo + hi) / 2 can cause overflow errors if lo and hi are very large
            int mid = lo + ((hi - lo) / 2);

            if (arr[mid] == target) {
                found = true;
                break;
            }

            if (arr[mid] < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return found;
    }

    // Time Complexity: O(log(n))
    private static boolean binarySearchRecursive(int[] arr, int target, int lo, int hi) {
        if (lo >= hi) {
            return false;
        }

        int mid = lo + ((hi - lo) / 2);

        if (arr[mid] == target) {
            return true;
        }

        if (arr[mid] < target) {
            return binarySearchRecursive(arr, target, mid + 1, hi);
        } else {
            return binarySearchRecursive(arr, target, lo, mid - 1);
        }
    }

    /**
     * Personal attempt based on https://www.youtube.com/watch?v=GU7DpgHINWQ
     * Time Complexity: O(log(n))
     * 
     * 
     * INVALID: this approach assumes you can throw as many balls as there are midpoints
     */
    private static int twoCrystalBalls(boolean[] breaks) {
        int firstBreakIndex = -1;
        int lo = 0;
        int hi = breaks.length - 1;

        while (lo <= hi) {
            int mid = lo + ((hi - lo) / 2);

            if (breaks[mid]) {
                // if breaks[mid] is true, then we have found a possible solution
                firstBreakIndex = mid;
                // However, we also may have passed another solution, meaning we have to look to the left to figure out if there is a more optimal floor
                // We keep searching until the entire solution space is covered because there is no way to determine whether the solution we encounter is the first one without searching part of the space linearly
                hi = mid - 1;
            } else {
                // if breaks[mid] is false, then we know we aren't searching high enough yet, meaning we have to look to the right
                lo = mid + 1;
            }
        }
        
        return firstBreakIndex;
    }

    /**
     * Personal attempt of in class algorithm
     * Assumes size of input is big enough to have sqrt
     * Time Complexity: O(sqrt(n))
     */
    private static int twoCrystalBallsSqrt(boolean[] breaks) {
        int jumpAmount = (int) Math.sqrt((double) breaks.length);
        int lo = 0;
        int hi = jumpAmount;
        int index = -1;

        while (lo <= hi) {
            // if true, walk from low up through the space of sqrt(n)
            if (breaks[hi]) {
                // if breaks[lo] is true, it will be the first instance
                if (breaks[lo]) {
                    index = lo;
                    break;
                }
                lo++;
            // if false, adjust range to next sqrt(n) space
            } else {
                lo = hi;
                hi += jumpAmount;
            }
        }

        return index;
    }

    /**
     * In class implementation
     */
    private static int twoCrystalBallsSqrtPrimeagen(boolean[] breaks) {
        int jumpAmount = (int) Math.sqrt((double) breaks.length);
        int breakIndex = -1;

        // Jump by sqrt(n) until first ball breaks
        for (int i=0; i<breaks.length; i+=jumpAmount) {
            if (breaks[i]) {
                breakIndex = i;
                break;
            }
        }

        // Walk up from the lower bound of the solution space until the first true is found
        int lowerBound = breakIndex - jumpAmount;
        // lowerBound>=0 added because lowerBound could be negative if jump amount is larger than break index, leading to an ArrayIndexOutOfBoundsException
        for (int i=lowerBound; i<breakIndex && lowerBound>=0; i++) {
            if (breaks[i]) {
                breakIndex = i;
                break;
            }
        }
        
        return breakIndex;
    }
}
