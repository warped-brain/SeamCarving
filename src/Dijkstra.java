import java.util.PriorityQueue;

public class Dijkstra {
    public static int[] dijkstra(int[][] graph, int source) {
        int numNodes = graph.length;

        // Create distance array and initialize it to infinity
        int[] distance = new int[numNodes];
        boolean[] visited = new boolean[numNodes];
        for (int i = 0; i < numNodes; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[source] = 0;

        // Create a priority queue to store nodes to be processed
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.add(source);

        // While the priority queue is not empty
        while (!pq.isEmpty()) {
            int node = pq.poll();
            visited[node] = true;

            // Iterate through the edges of the current node
            for (int i = 0; i < numNodes; i++) {
                if (graph[node][i] != 0) {
                    int neighbor = i;
                    int weight = graph[node][i];

                    // Relax the edge
                    if (distance[neighbor] > distance[node] + weight) {
                        distance[neighbor] = distance[node] + weight;
                        if (!visited[neighbor]) {
                            pq.add(neighbor);
                        }
                    }
                }
            }
        }
        return distance;
    }
}