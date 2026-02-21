import java.util.*;

/*
 * Dynamic Network Vulnerability
 * Find all Critical Links (Bridges) in an undirected graph
 * Time Complexity: O(V + E)
 */

public class CriticalLinks {

    private int time = 0;
    private List<List<Integer>> graph;
    private int[] disc;
    private int[] low;
    private boolean[] visited;
    private List<List<Integer>> bridges;

    public CriticalLinks(int vertices) {
        graph = new ArrayList<>();
        for (int i = 0; i < vertices; i++) {
            graph.add(new ArrayList<>());
        }

        disc = new int[vertices];
        low = new int[vertices];
        visited = new boolean[vertices];
        bridges = new ArrayList<>();
    }

    // Add undirected edge
    public void addEdge(int u, int v) {
        graph.get(u).add(v);
        graph.get(v).add(u);
    }

    // DFS function (Tarjan's)
    private void dfs(int u, int parent) {
        visited[u] = true;
        disc[u] = low[u] = ++time;

        for (int v : graph.get(u)) {

            if (v == parent) continue;

            if (!visited[v]) {
                dfs(v, u);

                low[u] = Math.min(low[u], low[v]);

                // Bridge condition
                if (low[v] > disc[u]) {
                    bridges.add(Arrays.asList(u, v));
                }

            } else {
                low[u] = Math.min(low[u], disc[v]);
            }
        }
    }

    // Find all bridges
    public List<List<Integer>> findBridges(int vertices) {

        for (int i = 0; i < vertices; i++) {
            if (!visited[i]) {
                dfs(i, -1);
            }
        }

        return bridges;
    }

    // Example usage
    public static void main(String[] args) {

        int V = 5;

        CriticalLinks network = new CriticalLinks(V);

        network.addEdge(0, 1);
        network.addEdge(1, 2);
        network.addEdge(2, 0);
        network.addEdge(1, 3);
        network.addEdge(3, 4);

        List<List<Integer>> result = network.findBridges(V);

        System.out.println("Critical Links (Bridges):");
        for (List<Integer> bridge : result) {
            System.out.println(bridge.get(0) + " - " + bridge.get(1));
        }
    }
}
