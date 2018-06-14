public class Iterative {

    static void iterativeBellmanFord(Graph g) {

        // Step 1: Initialize distances from src to all other
        // vertices as INFINITE
        for (int i = 1; i <= g.vertices; ++i) {
            g.updateDistance(i, Integer.MAX_VALUE);
        }
        g.updateDistance(g.start, 0);

        // Step 2: Relax all edges |V| - 1 times. A simple
        // shortest path from src to any other vertex can
        // have at-most |V| - 1 edges
        for (int i = 1; i < g.vertices; ++i) {
            for (int j = 0; j < g.edges.size(); ++j) {
                int u = g.edges.get(j).src;
                int v = g.edges.get(j).dest;
                int weight = g.edges.get(j).weight;
                if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                    g.updateDistance(v, g.dist[u] + weight);
                }
            }
        }

        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j = 0; j < g.edges.size(); ++j) {
            int u = g.edges.get(j).src;
            int v = g.edges.get(j).dest;
            int weight = g.edges.get(j).weight;
            if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                System.out.println("Graph contains negative weight cycle");
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph("graphs/graph2.in");

        // compute bellman-ford
        long startTime = System.currentTimeMillis();
        iterativeBellmanFord(g);
        long endTime = System.currentTimeMillis();

        System.out.println("Done. Elapsed time: " + (endTime - startTime));
        g.printResult();

    }
}
