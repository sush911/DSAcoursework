/*Imagine you're on a challenging hiking trail represented by an array nums, where each element represents the
altitude at a specific point on the trail. You want to find the longest consecutive stretch of the trail you can hike
while staying within a certain elevation gain limit (at most k).*/


public class Qno5b {

    public static void main(String[] args) {
        int[] trail = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;
        System.out.println("Longest hike length: " + findLongestHike(trail, k));
    }

    public static int findLongestHike(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;

        int maxLength = 0;
        int start = 0;

        for (int end = 1; end < nums.length; end++) {
            // Check if the current pair of elements satisfies the condition
            if (Math.abs(nums[end] - nums[end - 1]) > k) {
                start = end; // Start a new subarray from this position
            }
            // Update the maximum length of valid subarray
            maxLength = Math.max(maxLength, end - start + 1);
        }

        return maxLength;
    }
}
