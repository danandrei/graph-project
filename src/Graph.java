import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Graph {
    int vertices;
    int dist[];
    int start;
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
    }

    synchronized void updateDistance(Integer index, Integer distance) {
        dist[index] = distance;
    }

    synchronized int getDistance(Integer index) {
        return dist[index];
    }

    void printResult() {
        for (int j = 1; j <= vertices; ++j) {
            System.out.println(j + " -> " + dist[j]);
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
            dist = new int[vertices + 1];

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
