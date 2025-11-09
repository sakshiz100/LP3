import java.util.Arrays;
import java.util.Scanner;

public class A4 {

    /**
     * Solves the 0/1 Knapsack problem using the bottom-up Dynamic Programming
     * approach.
     * Time Complexity: O(n*W)
     * Space Complexity: O(n*W) for the DP table
     */
    public static int knapSack(int W, int[] wt, int[] val, int n) {
        // K[i][w] will store the maximum value with knapsack capacity 'w'
        // considering first 'i' items.
        int[][] K = new int[n + 1][W + 1];

        // Build table K[][] in bottom-up manner [cite: 339]
        for (int i = 0; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    // Base case: 0 items or 0 capacity, max value is 0 [cite: 340, 342]
                    K[i][w] = 0;
                } else if (wt[i - 1] <= w) {
                    // Current item i (index i-1) can be included.
                    // Choose the max of:
                    // 1. Including item i: val[i-1] + K[i-1][w - wt[i-1]]
                    // 2. Excluding item i: K[i-1][w]
                    K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]); // [cite: 346, 347]
                } else {
                    // Current item i cannot be included, max value is the same as excluding it.
                    K[i][w] = K[i - 1][w]; // [cite: 349]
                }
            }
        }

        // K[n][W] holds the final maximum value
        return K[n][W];
    }

    // --- Main method for demonstration ---
    public static void main(String[] args) {
        int[] val = { 60, 100, 120 }; // values
        int[] wt = { 10, 20, 30 }; // weights
        int W = 50; // max weight capacity
        int n = val.length; // number of items

        int maxValue = knapSack(W, wt, val, n);
        System.out.println("Maximum value for 0/1 Knapsack (DP) is: " + maxValue);

        System.out.println("\nTime Complexity: O(n*W) where n is number of items and W is the capacity. [cite: 356]");
        System.out.println("Space Complexity: O(n*W) for the DP table.");
    }
}