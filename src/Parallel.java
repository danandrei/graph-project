import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Parallel {

    static void parallelBellmanFord(int threadCount, Graph g) throws InterruptedException {
        Thread[] threads = new Thread[threadCount];
        ReadWriteLock lock = new ReentrantReadWriteLock();


        // parallel implementation
        for (int threadId = 0; threadId < threadCount; ++threadId) {
            threads[threadId] = new Thread(() -> {
                int id = Integer.parseInt(Thread.currentThread().getName());

                for (int i = 0; i <= g.vertices; ++i) {
                    for (int j = id; j < g.edges.size(); j += threadCount) {
                        Graph.Edge edge = g.edges.get(j);

                        Boolean condition = g.getDist(edge.src) != Integer.MAX_VALUE && (g.getDist(edge.src) + edge.weight < g.getDist(edge.dest));

                        if (condition) {
                            g.setDist(edge.dest, g.getDist(edge.src) + edge.weight);
                        }

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


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int threadCount = scanner.nextInt();
        Graph g = new Graph("graphs/graph3.in");

        // compute bellman-ford
        long startTime = System.nanoTime();
        try {
            parallelBellmanFord(threadCount, g);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long estimatedTime = System.nanoTime() - startTime;
        double endTime = ((Long) estimatedTime).doubleValue();

        System.out.println("Done. Elapsed time: " + endTime / 1000000);

        //g.printResult();

    }
}
