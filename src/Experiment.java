import java.util.Random;

public class Experiment {

    public void runTraversals(Graph g, int startVertex, boolean printSequence) {
        if (printSequence) {
            System.out.print(" -> BFS Traversal Sequence: ");
            g.bfs(startVertex);
            System.out.println();

            System.out.print(" -> DFS Traversal Sequence: ");
            g.dfs(startVertex);
            System.out.println();
        } else {
            long sBfs = System.nanoTime();
            g.bfs(startVertex);
            long eBfs = System.nanoTime();

            long sDfs = System.nanoTime();
            g.dfs(startVertex);
            long eDfs = System.nanoTime();
        }
    }

    public void runMultipleTests() {
        int[] graphSizes = {10, 30, 100};

        System.out.println("=========================================================");
        System.out.println("            EXPERIMENTAL ANALYSIS PERFORMANCES           ");
        System.out.println("=========================================================");

        for (int size : graphSizes) {
            Graph g = generateRandomGraph(size);
            System.out.println("\n[Testing Graph Size: " + size + " Vertices]");

            if (size == 10) {
                System.out.println("--- Graph Structure Output ---");
                g.printGraph();
                System.out.println("\n--- Traversal Outputs ---");
                runTraversals(g, 0, true);

                System.out.println("\n--- Bonus Target: Dijkstra Implementation ---");
                g.dijkstra(0);
            }

            long startBfs = System.nanoTime();
            g.bfs(0);
            long endBfs = System.nanoTime();

            long startDfs = System.nanoTime();
            g.dfs(0);
            long endDfs = System.nanoTime();

            System.out.println("\n--- Performance Metrics Table Info ---");
            System.out.println(" * BFS execution time: " + (endBfs - startBfs) + " ns");
            System.out.println(" * DFS execution time: " + (endDfs - startDfs) + " ns");
            System.out.println("---------------------------------------------------------");
        }
    }

    private Graph generateRandomGraph(int numVertices) {
        Graph g = new Graph();
        Random rand = new Random(42);

        for (int i = 0; i < numVertices; i++) {
            g.addVertex(new Vertex(i));
        }

        for (int i = 0; i < numVertices - 1; i++) {
            g.addEdge(i, i + 1, rand.nextInt(10) + 1);
        }

        int extraEdges = numVertices * 2;
        for (int i = 0; i < extraEdges; i++) {
            int from = rand.nextInt(numVertices);
            int to = rand.nextInt(numVertices);
            if (from != to) {
                g.addEdge(from, to, rand.nextInt(15) + 1);
            }
        }
        return g;
    }
}