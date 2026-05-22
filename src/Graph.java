import java.util.*;

public class Graph {
    private final Map<Integer, List<Edge>> adjacencyList;
    private final Map<Integer, Vertex> vertices;

    public Graph() {
        this.adjacencyList = new HashMap<>();
        this.vertices = new HashMap<>();
    }

    public void addVertex(Vertex v) {
        if (!vertices.containsKey(v.getId())) {
            vertices.put(v.getId(), v);
            adjacencyList.put(v.getId(), new ArrayList<>());
        }
    }

    public void addEdge(int from, int to, int weight) {
        if (!vertices.containsKey(from)) addVertex(new Vertex(from));
        if (!vertices.containsKey(to)) addVertex(new Vertex(to));

        Vertex src = vertices.get(from);
        Vertex dest = vertices.get(to);

        Edge edge = new Edge(src, dest, weight);
        adjacencyList.get(from).add(edge);
    }

    public void addEdge(int from, int to) {
        addEdge(from, to, 1);
    }

    public void printGraph() {
        for (int key : adjacencyList.keySet()) {
            System.out.print("Vertex " + key + " adjacent edges: ");
            System.out.println(adjacencyList.get(key));
        }
    }

    public void bfs(int start) {
        if (!vertices.containsKey(start)) return;

        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            System.out.print(current + " ");

            for (Edge edge : adjacencyList.get(current)) {
                int neighbor = edge.getDestination().getId();
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
    }

    public void dfs(int start) {
        if (!vertices.containsKey(start)) return;
        Set<Integer> visited = new HashSet<>();
        dfsHelper(start, visited);
    }

    private void dfsHelper(int current, Set<Integer> visited) {
        visited.add(current);
        System.out.print(current + " ");

        for (Edge edge : adjacencyList.get(current)) {
            int neighbor = edge.getDestination().getId();
            if (!visited.contains(neighbor)) {
                dfsHelper(neighbor, visited);
            }
        }
    }

    public void dijkstra(int start) {
        if (!vertices.containsKey(start)) {
            System.out.println("Start vertex not found.");
            return;
        }

        List<Integer> vertexKeys = new ArrayList<>(vertices.keySet());
        int n = vertexKeys.size();

        Map<Integer, Integer> dist = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int vId : vertexKeys) {
            dist.put(vId, Integer.MAX_VALUE);
        }
        dist.put(start, 0);

        for (int i = 0; i < n; i++) {
            int u = -1;
            int minDistance = Integer.MAX_VALUE;

            for (int vId : vertexKeys) {
                if (!visited.contains(vId) && dist.get(vId) < minDistance) {
                    minDistance = dist.get(vId);
                    u = vId;
                }
            }

            if (u == -1) break;
            visited.add(u);

            for (Edge edge : adjacencyList.get(u)) {
                int v = edge.getDestination().getId();
                if (!visited.contains(v)) {
                    int newDist = dist.get(u) + edge.getWeight();
                    if (newDist < dist.get(v)) {
                        dist.put(v, newDist);
                    }
                }
            }
        }

        System.out.println("\nShortest paths from Vertex (" + start + "):");
        for (Map.Entry<Integer, Integer> entry : dist.entrySet()) {
            String cost = entry.getValue() == Integer.MAX_VALUE ? "Unreachable" : String.valueOf(entry.getValue());
            System.out.println("  To Vertex " + entry.getKey() + " -> Minimum Cost / Distance: " + cost);
        }
    }
}