package graph;

public class Edge {
    private Vertex to;

    public Edge(Vertex vertex) {
        to = vertex;
    }

    public Vertex getDestination() {
        return to;
    }
}