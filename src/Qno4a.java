/*Imagine you're a city planner tasked with improving the road network between key locations (nodes) represented
by numbers 0 to n-1. Some roads (edges) have known travel times (positive weights), while others are under
construction (weight -1). Your goal is to modify the construction times on these unbuilt roads to achieve a specific
travel time (target) between two important locations (from source to destination).*/


import java.util.Arrays;

public class Qno4a {

    static class Edge {
        int source, destination, time;

        Edge(int source, int destination, int time) {
            this.source = source;
            this.destination = destination;
            this.time = time;
        }
    }

    // Method to run Bellman-Ford algorithm and find the shortest path
    static int[] bellmanFord(Edge[] edges, int V, int src) {
        int[] dist = new int[V];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        // Relax all edges |V| - 1 times
        for (int i = 1; i < V; i++) {
            for (Edge edge : edges) {
                if (dist[edge.source] != Integer.MAX_VALUE &&
                        dist[edge.source] + edge.time < dist[edge.destination]) {
                    dist[edge.destination] = dist[edge.source] + edge.time;
                }
            }
        }
        return dist;
    }

    // Method to adjust road weights
    public static int[][] adjustRoadWeights(int V, int[][] roads, int src, int dest, int targetTime) {
        // Convert road list to edge list
        Edge[] edges = new Edge[roads.length];
        for (int i = 0; i < roads.length; i++) {
            edges[i] = new Edge(roads[i][0], roads[i][1], roads[i][2]);
        }

        // Initial Bellman-Ford to check if targetTime is achievable with current weights
        int[] dist = bellmanFord(edges, V, src);

        // If the direct path is already achievable
        if (dist[dest] == targetTime) {
            return roads;
        }

        // Adjust weights of the roads with -1
        for (int i = 0; i < roads.length; i++) {
            if (roads[i][2] == -1) {
                // Binary search for minimum weight that satisfies the target time
                int low = 1, high = 2 * 1000000000; // 2 * 10^9
                while (low < high) {
                    int mid = (low + high) / 2;
                    // Update edge weight
                    edges[i] = new Edge(roads[i][0], roads[i][1], mid);
                    int[] newDist = bellmanFord(edges, V, src);

                    if (newDist[dest] == targetTime) {
                        roads[i][2] = mid;
                        break;
                    }

                    if (newDist[dest] < targetTime) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            }
        }

        return roads;
    }

    public static void main(String[] args) {
        int V = 5;
        int[][] roads = {
                {4, 1, -1},
                {2, 0, -1},
                {0, 3, -1},
                {4, 3, -1}
        };
        int src = 0;
        int dest = 1;
        int targetTime = 5;

        int[][] modifiedRoads = adjustRoadWeights(V, roads, src, dest, targetTime);

        // Output the modified roads
        System.out.println("Modified roads:");
        for (int[] road : modifiedRoads) {
            System.out.println(Arrays.toString(road));
        }
    }
}
