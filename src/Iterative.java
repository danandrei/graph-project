public class Iterative {

    static void iterativeBellmanFord(Graph g) {


        for (int i = 1; i < g.vertices; ++i) {

            for (int j = 0; j < g.edges.size(); ++j) {
                Graph.Edge edge = g.edges.get(j);

                if (g.getDist(edge.src) != Integer.MAX_VALUE && (g.getDist(edge.src) + edge.weight < g.getDist(edge.dest))) {
                    g.setDist(edge.dest, g.getDist(edge.src) + edge.weight);
                }
            }
        }

        for (int j = 0; j < g.edges.size(); ++j) {
            Graph.Edge edge = g.edges.get(j);

            if (g.getDist(edge.src) != Integer.MAX_VALUE && (g.getDist(edge.src) + edge.weight < g.getDist(edge.dest))) {
                System.out.println("Graph contains negative weight cycle");
            }
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph("graphs/graph3.in");

        // compute bellman-ford
        long startTime = System.nanoTime();
        iterativeBellmanFord(g);
        long estimatedTime = System.nanoTime() - startTime;
        double endTime = ((Long) estimatedTime).doubleValue();

        System.out.println("Done. Elapsed time: " + endTime / 1000000);
       //g.printResult();

    }
}
