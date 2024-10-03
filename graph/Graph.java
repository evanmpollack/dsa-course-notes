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

        Map<Vertex, List<Vertex>> adjList = createAdjList(List.of(start, one, two, three, four));

        // Same graph created with vertex class
        int[][] adjMatrix = new int[][] {
            { 0, 1, 0, 0, 1 },
            // directed so there is technicaly no edge at (1, 0) even though (0, 1) exists
            { 0, 0, 1, 0, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 1, 1, 0 }
        };

        for (int i = 0; i < 6; i++) {
            System.out.println("DFS Class (target=" + i + "): " + dfs(start, new HashSet<>(), i));
            System.out.println("BFS Class (target=" + i + "): " + bfs(start, i));
            System.out.println("DFS List (target=" + i + "): " + dfs(start, adjList, new HashSet<>(), i));
            System.out.println("BFS List (target=" + i + "): " + bfs(start, adjList, i));
            System.out.println("DFS Matrix (target=" + i + "): " + dfs(0, adjMatrix, new HashSet<>(), i));
            System.out.println("BFS Matrix (target=" + i + "): " + bfs(0, adjMatrix, i));
        }
    }

    // ---------------------------------------------------------------------------------------------------

    // Personal Implementations of BFS/DFS on Object-based, adjacency list, and adjacency matrix


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

    // Adjacency List - Using vertex class is a bit redundant because the class already has a list of edges in which verticies can be accessed
    // However, this version also works for graphs that don't have associated Vertex/Edge classes
    private static boolean dfs(Vertex curr, Map<Vertex, List<Vertex>> adjList, Set<Vertex> visited, int target) {
        if (curr == null || visited.contains(curr)) {
            return false;
        }

        visited.add(curr);
        
        if (curr.getData() == target) {
            return true;
        }

        boolean found = false;
        for (Vertex v : adjList.get(curr)) {
            found = found || dfs(v, adjList, visited, target);
        }
        return found;
    }

    // Adjacency Matrix DFS
    private static boolean dfs(int curr, int[][] adjMatrix, Set<Integer> visited, int target) {
        int[] edges = adjMatrix[curr];
        boolean found = false;

        if (visited.contains(curr)) {
            return false;
        }

        visited.add(curr);

        if (curr == target) {
            return true;
        }

        for (int i = 0; i < edges.length; i++) {
            // We only want to check edges that exist
            if (edges[i] != 0) {
                found = found || dfs(i, adjMatrix, visited, target);
            }
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

    // Adjacency List BFS
    private static boolean bfs(Vertex start, Map<Vertex, List<Vertex>> adjList, int target) {
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        boolean found = false;

        if (start != null) {
            queue.add(start);
        }

        while (!queue.isEmpty()) {
            Vertex curr = queue.remove();
            visited.add(curr);

            if (curr.getData() == target) {
                found = true;
                break;
            }

            for (Vertex v : adjList.get(curr)) {
                if (!visited.contains(v)) {
                    queue.add(v);
                }
            }
        }
        return found;
    }

    // Adjacency Matrix BFS
    private static boolean bfs(int start, int[][] graph, int target) {
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        boolean found = false;

        queue.add(start);

        while (!queue.isEmpty()) {
            int curr = queue.remove();
            visited.add(curr);

            if (curr == target) {
                found = true;
                break;
            }

            for (int i = 0; i < graph[curr].length; i++) {
                if (graph[curr][i] != 0 && !visited.contains(i)) {
                    queue.add(i);
                }
            }
        }
        return found;
    }

    // ---------------------------------------------------------------------------------------------------

    private static Map<Vertex, List<Vertex>> createAdjList(List<Vertex> nodes) {
        Map<Vertex, List<Vertex>> adjList = new HashMap<>();
        for (Vertex v : nodes) {
            List<Vertex> neighbors = new ArrayList<>();
            for (Edge e : v.getNeighbors()) {
                neighbors.add(e.getDestination());
            }
            adjList.put(v, neighbors);
        }
        return adjList;
    }

    // ---------------------------------------------------------------------------------------------------

    // In Class Implementations: 
}