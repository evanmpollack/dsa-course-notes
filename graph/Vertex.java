package graph;

import java.util.*;

public class Vertex {
    private int data;
    private List<Edge> neighbors;

    public Vertex(int data) {
        this.data = data;
        neighbors = new ArrayList<>();
    }

    public int getNumberOfNeighbors() {
        return this.neighbors.size();
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Vertex v) {
        neighbors.add(new Edge(v));
    }
 
    public int getData() {
        return data;
    }
}