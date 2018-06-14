import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class Parallel {

    static void parallelBellmanFord(int threadCount, Graph g) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Thread[] threads = new Thread[threadCount];

        // init distances
        for (int i = 1; i <= g.vertices; ++i) {
            g.updateDistance(i, Integer.MAX_VALUE);
        }
        g.updateDistance(g.start, 0);

        // parallel implementation
        for (int threadId = 0; threadId < threadCount; ++threadId) {
            threads[threadId] = new Thread(() -> {
                int id = Integer.parseInt(Thread.currentThread().getName());

                for (int i = 0; i <= g.vertices; ++i) {
                    for (int j = id; j < g.edges.size(); j += threadCount) {
                        Graph.Edge edge = g.edges.get(j);
                        lock.lock();
                        if (g.dist[edge.src] != Integer.MAX_VALUE && g.dist[edge.src] + edge.weight < g.dist[edge.dest]) {
                            g.dist[edge.dest] = g.dist[edge.src] + edge.weight;
                        }
                        lock.unlock();
                    }
                }

            }, Integer.toString(threadId));
            threads[threadId].start();
        }

        // wait for threads to stop
        for (int i = 0; i < threadCount; ++i) {
            threads[i].join();
        }
    }

    static void iterativeBellmanFord(Graph g) {
        int runs = 0;
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
                runs += 1;
                if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                    g.updateDistance(v, g.dist[u] + weight);
                }
            }
        }
        System.out.println("iterative " + runs);
        // Step 3: check for negative-weight cycles.  The above
        // step guarantees shortest distances if graph doesn't
        // contain negative weight cycle. If we get a shorter
        //  path, then there is a cycle.
        for (int j = 0; j < g.edges.size(); ++j) {
            int u = g.edges.get(j).src;
            int v = g.edges.get(j).dest;
            int weight = g.edges.get(j).weight;
            if (g.dist[u] != Integer.MAX_VALUE && g.dist[u] + weight < g.dist[v]) {
                //System.out.println("Graph contains negative weight cycle");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int threadCount = scanner.nextInt();
        Graph g = new Graph("graphs/graph2.in");

        // compute bellman-ford
        long startTime = System.currentTimeMillis();
        try {
            parallelBellmanFord(threadCount, g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Done. Elapsed time: " + (endTime - startTime));

        // check if it is valid
        Graph g2 = new Graph("graphs/graph2.in");
        iterativeBellmanFord(g2);
        boolean correct = true;
        for (int i = 1; i <= g2.vertices; ++i) {
            if (g.dist[i] != g2.dist[i]) {
                correct = false;
            }
        }
        System.out.println(correct);
        //g.printResult();

    }
}
