package graph;

import java.util.*;

public class Graph {
    public static void main(String[] args) {
        Vertex start = new Vertex(0);
        Vertex three = new Vertex(3);
        Vertex two = new Vertex(2);
        two.addNeighbor(three);
        Vertex four = new Vertex(4);
        four.addNeighbor(three);
        four.addNeighbor(two);
        Vertex one = new Vertex(1);
        one.addNeighbor(two);
        start.addNeighbor(one);
        start.addNeighbor(four);
        for (int i = 0; i < 6; i++) {
            System.out.println("DFS (target=" + i + "): " + dfs(start, new HashSet<>(), i));
            System.out.println("BFS (target=" + i + "): " + bfs(start, i));
        }
    }

    // Unweighted Object-based Graph DFS
    private static boolean dfs(Vertex curr, Set<Vertex> visited, int target) {
        // if input is null
        if (curr == null || visited.contains(curr)) {
            return false;
        } 

        visited.add(curr);

        if (curr.getData() == target) {
            return true;
        }

        boolean found = false;
        for (Edge e : curr.getNeighbors()) {
            // short circuits if found is true, preventing unneccesary recursive calls
            found = found || dfs(e.getDestination(), visited, target);
        }

        return found;
    }

    // Unweighted Object-based Graph BFS
    private static boolean bfs(Vertex start, int target) {
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        boolean found = false;

        if (start == null) {
            return found;
        }

        queue.add(start);

        while (!queue.isEmpty()) {
            Vertex curr = queue.remove();
            
            visited.add(curr);

            if (curr.getData() == target) {
                found = true;
                break;
            }

            for (Edge e : curr.getNeighbors()) {
                Vertex neighbor = e.getDestination();
                // Duplicate neighbors can exist in the queue, but the avoids infinite loop
                // Consider the graph [[0, 1], [0, 4], [1, 2], [2, 3], [4, 2], [4, 3]]
                // Because 4 and 1 share a neighbor (2) and both are processed before the neighbor, 
                // that means that two will be added to the queue twice, 
                // as it won't be added to the set until it is popped at the next level
                // (same will happen for 3 because 2 and 4 are processed before the first 3)
                if (!visited.contains(neighbor)) {
                    // System.out.println(neighbor.getData());
                    queue.add(neighbor);
                }
            }
        }
        
        return found;
    }
}