import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Graph {
    int vertices;
    int start;
    List<Integer> dist = new CopyOnWriteArrayList<>();
    List<Edge> edges = new ArrayList<>();

    // A class to represent a weighted edge in graph
    class Edge {
        int src, dest, weight;

        Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }
    }

    Graph(String filename) {

        this.load(filename);

        // init dist
        for (int i = 0; i <= vertices; ++i) {
            dist.add(Integer.MAX_VALUE);
        }
        dist.set(start, 0);

    }

    void setDist(int index, int value) {
        dist.set(index, value);
    }

    int getDist(int index) {
        return dist.get(index);
    }

    void printResult() {
        for (int j = 1; j <= vertices; ++j) {
            System.out.println(j + " -> " + dist.get(j));
        }
    }

    private void load (String filename) {
        System.out.println("Loading graph...");
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;

            // read first line
            line = br.readLine();
            Integer[] firstLine = processLine(line);
            vertices = firstLine[0];
            start = firstLine[2];

            for (int i = 0; i < firstLine[1]; i++) {
                line = br.readLine();
                if (line == null) {
                    break;
                }

                Integer[] processedLine = processLine(line);
                this.addEdge(processedLine[0], processedLine[1], processedLine[2]);
            }

            System.out.println("Graph loaded.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addEdge(int src, int dest, int weight) {
        this.edges.add(new Edge(src, dest, weight));
    }

    private Integer[] processLine(String line) {
        List<String> parts = Arrays.asList(line.split(" "));

        Integer[] result = new Integer[3];
        result[0] = Integer.parseInt(parts.get(1));
        result[1] = Integer.parseInt(parts.get(2));
        result[2] = Integer.parseInt(parts.get(3));

        return result;
    }
}
