/*Imagine you're at a movie theater with assigned seating. You have a seating chart represented by an array nums
where nums[i] represents the seat number at row i. You're looking for two friends to sit together, considering their
seat preferences and your limitations*/


import java.util.*;

public class Qno2b {

    public static boolean canSitTogether(int[] nums, int indexDiff, int valueDiff) {
        // Iterate through each seat
        for (int i = 0; i < nums.length; i++) {
            // Check the seats within the indexDiff range
            for (int j = i + 1; j <= i + indexDiff && j < nums.length; j++) {
                // Check if the absolute difference in seat numbers is within valueDiff
                if (Math.abs(nums[i] - nums[j]) <= valueDiff) {
                    return true; // Found a valid pair
                }
            }
        }
        return false; // No valid pairs found
    }

    public static void main(String[] args) {
        int[] nums1 = {2, 3, 5, 4, 9};
        int indexDiff1 = 2;
        int valueDiff1 = 1;

        System.out.println(canSitTogether(nums1, indexDiff1, valueDiff1)); // Output: true

        int[] nums2 = {1, 6, 7, 10};
        int indexDiff2 = 2;
        int valueDiff2 = 3;

        System.out.println(canSitTogether(nums2, indexDiff2, valueDiff2)); // Output: true
    }
}
