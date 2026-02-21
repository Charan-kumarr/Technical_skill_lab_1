import java.util.*;

/*
 * Optimal Resource Allocation (Assignment Problem)
 * Using Bitmask DP (State Compression)
 *
 * Time Complexity: O(2^N * N^2)
 * Space Complexity: O(2^N)
 */

public class OptimalResourceAllocation {

    static int N;
    static int[][] cost;
    static int[] dp;

    // Recursive function using Bitmask
    static int solve(int mask) {

        // Number of workers already assigned
        int worker = Integer.bitCount(mask);

        // Base case: all workers assigned
        if (worker == N)
            return 0;

        // If already computed
        if (dp[mask] != -1)
            return dp[mask];

        int ans = Integer.MAX_VALUE;

        // Try assigning current worker to every unassigned task
        for (int task = 0; task < N; task++) {

            // If task not assigned yet
            if ((mask & (1 << task)) == 0) {

                int newMask = mask | (1 << task);

                ans = Math.min(ans,
                        cost[worker][task] + solve(newMask));
            }
        }

        return dp[mask] = ans;
    }

    public static void main(String[] args) {

        N = 3;

        cost = new int[][]{
                {9, 2, 7},
                {6, 4, 3},
                {5, 8, 1}
        };

        dp = new int[1 << N];
        Arrays.fill(dp, -1);

        int minimumCost = solve(0);

        System.out.println("Minimum Assignment Cost: " + minimumCost);
    }
}
