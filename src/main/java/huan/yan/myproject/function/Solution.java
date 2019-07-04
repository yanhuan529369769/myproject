package huan.yan.myproject.function;

import java.util.Arrays;
import java.util.Collections;

public class Solution {


    /*给定 nums = [1,2,3,5,8,11,15], target = 9

    因为 nums[0] + nums[1] = 2 + 7 = 9
    所以返回 [0, 1]*/

    public static void main(String[] args) {
        int[] nums = {3,2,4};
        int[] ints = twoSum(nums, 6);
        System.out.println(ints);


    }
    public static int[] twoSum(int[] nums, int target) {

        for (int i = 0; i < nums.length/2 + 1; i++) {
            for (int j = nums.length/2  + 1; j < nums.length; j++) {

                if (nums[i] + nums[j] == target) return new int[]{i, j};

            }
        }

        return null;
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int n1 = nums1.length;
        int n2 = nums2.length;
        if (n1 > n2)
            return findMedianSortedArrays(nums2, nums1);
        int k = (n1 + n2 + 1) / 2;
        int left = 0;
        int right = n1;
        while (left < right) {
            int m1 = left + (right - left) / 2;
            int m2 = k - m1;
            if (nums1[m1] < nums2[m2 - 1])
                left = m1 + 1;
            else
                right = m1;
        }
        int m1 = left;
        int m2 = k - left;
        int c1 = Math.max(m1 <= 0 ? Integer.MIN_VALUE : nums1[m1 - 1],
                m2 <= 0 ? Integer.MIN_VALUE : nums2[m2 - 1]);
        if ((n1 + n2) % 2 == 1)
            return c1;
        int c2 = Math.min(m1 >= n1 ? Integer.MAX_VALUE : nums1[m1],
                m2 >= n2 ? Integer.MAX_VALUE : nums2[m2]);
        return (c1 + c2) * 0.5;
    }


}
