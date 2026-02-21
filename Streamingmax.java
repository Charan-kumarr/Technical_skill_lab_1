import java.util.*;

/*
 * Streaming Max Analytics
 * Monotonic Deque Approach
 *
 * Time Complexity:
 * Amortized O(1) per element
 * Overall O(N)
 *
 * Space: O(K)
 */

public class StreamingMax {

    private Deque<int[]> deque; // {value, index}
    private int k;
    private int index;

    public StreamingMax(int windowSize) {
        this.k = windowSize;
        this.index = 0;
        this.deque = new ArrayDeque<>();
    }

    public int add(int value) {

        // Remove smaller elements from back
        while (!deque.isEmpty() && deque.peekLast()[0] <= value) {
            deque.pollLast();
        }

        // Add new element
        deque.offerLast(new int[]{value, index});

        // Remove elements outside window
        if (!deque.isEmpty() && deque.peekFirst()[1] <= index - k) {
            deque.pollFirst();
        }

        index++;

        // If window formed, return max
        if (index >= k) {
            return deque.peekFirst()[0];
        }

        return -1; // window not formed yet
    }

    public static void main(String[] args) {

        StreamingMax monitor = new StreamingMax(3);

        int[] latencyData = {1, 3, -1, -3, 5, 3, 6, 7};

        System.out.println("Streaming Maximums:");

        for (int value : latencyData) {
            System.out.print(monitor.add(value) + " ");
        }
    }
}
