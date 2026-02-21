import java.util.*;

/*
 * Range Performance Monitor (Segment Tree)
 * Operations:
 * 1. update(index, value)
 * 2. queryMax(L, R)
 *
 * Time Complexity:
 * Build: O(N)
 * Update: O(log N)
 * Query: O(log N)
 */

public class SegmentTree {

    private int[] tree;
    private int n;

    // Constructor
    public SegmentTree(int[] arr) {
        n = arr.length;
        tree = new int[4 * n];
        build(arr, 0, 0, n - 1);
    }

    // Build the tree
    private void build(int[] arr, int node, int start, int end) {
        if (start == end) {
            tree[node] = arr[start];
        } else {
            int mid = (start + end) / 2;

            build(arr, 2 * node + 1, start, mid);
            build(arr, 2 * node + 2, mid + 1, end);

            tree[node] = Math.max(tree[2 * node + 1],
                                  tree[2 * node + 2]);
        }
    }

    // Internal update function
    private void update(int index, int value,
                        int node, int start, int end) {

        if (start == end) {
            tree[node] = value;
        } else {
            int mid = (start + end) / 2;

            if (index <= mid)
                update(index, value, 2 * node + 1, start, mid);
            else
                update(index, value, 2 * node + 2, mid + 1, end);

            tree[node] = Math.max(tree[2 * node + 1],
                                  tree[2 * node + 2]);
        }
    }

    // Public update
    public void update(int index, int value) {
        update(index, value, 0, 0, n - 1);
    }

    // Internal query
    private int query(int left, int right,
                      int node, int start, int end) {

        // No overlap
        if (right < start || end < left)
            return Integer.MIN_VALUE;

        // Complete overlap
        if (left <= start && end <= right)
            return tree[node];

        // Partial overlap
        int mid = (start + end) / 2;

        return Math.max(
                query(left, right, 2 * node + 1, start, mid),
                query(left, right, 2 * node + 2, mid + 1, end)
        );
    }

    // Public query
    public int queryMax(int left, int right) {
        return query(left, right, 0, 0, n - 1);
    }

    // Example usage
    public static void main(String[] args) {

        int[] prices = {1, 3, 5, 7, 9, 11};

        SegmentTree monitor = new SegmentTree(prices);

        System.out.println("Max between 1 and 4: "
                + monitor.queryMax(1, 4));

        monitor.update(2, 10);

        System.out.println("After update:");
        System.out.println("Max between 1 and 4: "
                + monitor.queryMax(1, 4));
    }
}
