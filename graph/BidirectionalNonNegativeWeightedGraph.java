package graph;

import java.util.*;

public class BidirectionalNonNegativeWeightedGraph<T> {
    private final Map<T, List<WeightedEdge<T>>> graph;

    public BidirectionalNonNegativeWeightedGraph() {
        graph = new HashMap<>();
    }

    public void addVertex(T node) {
        graph.putIfAbsent(node, new LinkedList<>());
    }

    public void addEdge(T source, T destination, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be non-negative to use Dijikstra's");
        }

        List<WeightedEdge<T>> sourceEdges = graph.get(source);
        List<WeightedEdge<T>> destinationEdges = graph.get(destination);

        if (sourceEdges == null || destinationEdges == null) {
            throw new IllegalArgumentException("Source and Destination must be in graph");
        }

        sourceEdges.add(new WeightedEdge<T>(destination, weight));
        destinationEdges.add(new WeightedEdge<T>(source, weight));
        graph.put(source, sourceEdges);
        graph.put(destination, destinationEdges);
    }

    public boolean dfs(T source, T target) {
        return dfs(source, target, new HashSet<>());
    }

    private boolean dfs(T source, T target, Set<T> visited) {
        if (source == null || visited.contains(source)) {
            return false;
        }

        if (source.equals(target)) {
            return true;
        }

        visited.add(source);
        // No need to getOrDefault. At this point, source will be in graph
        List<WeightedEdge<T>> edges = graph.get(source);
        boolean found = false;
        for (WeightedEdge<T> edge : edges) {
            found = found || dfs(edge.destination, target, visited);
        }
        return found;
    }

    public boolean bfs(T source, T target) {
        Set<T> visited = new HashSet<>();
        Queue<T> queue = new LinkedList<>();
        boolean found = false;

        if (source != null) {
            queue.add(source);
        }

        while (!queue.isEmpty()) {
            T curr = queue.remove();

            if (curr.equals(target)) {
                found = true;
                break;
            }

            List<WeightedEdge<T>> edges = graph.get(curr);
            for (WeightedEdge<T> edge : edges) {
                T next = edge.destination;
                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.add(next);
                }
            }
        }

        return found;
    }

    // Dijikstra's
    public List<T> shortestPath(T source, T target) {
        // Prevent extra work. No reason to search if source isn't in the graph
        if (graph.get(source) == null) {
            throw new IllegalArgumentException("Source must be in graph");
        }

        // Initialize helper structures: distance map, path map, visited set, and unvisited min heap
        Map<T, Integer> estimatedDistance = graph.keySet()
            .stream()
            .collect(HashMap::new, (map, key) -> map.put(key, Integer.MAX_VALUE), HashMap::putAll);
        Map<T, T> path = graph.keySet()
            .stream()
            .collect(HashMap::new, (map, key) -> map.put(key, null), HashMap::putAll);
        Set<T> visited = new HashSet<>();
        // unvisited nodes ranked by distance in ascending order
        Queue<DijikstraPair<T>> unvisited = new PriorityQueue<>((e1, e2) -> e1.distanceFromSource - e2.distanceFromSource);
        boolean found = false;

        // Update values for source
        estimatedDistance.put(source, 0);
        unvisited.add(new DijikstraPair<>(source, 0));
        visited.add(source);

        while (!unvisited.isEmpty()) {
            T curr = unvisited.remove().node;

            if (curr.equals(target)) {
                found = true;
                break;
            }

            List<WeightedEdge<T>> edges = graph.get(curr);
            for (WeightedEdge<T> edge : edges) {
                T next = edge.destination;
                int newDistance = estimatedDistance.get(curr) + edge.weight;
                int currentDistance = estimatedDistance.get(next);
                // Update the estimated distance and path for the next node if we have found a shorter path
                if (newDistance < currentDistance) {
                    estimatedDistance.put(next, newDistance);
                    path.put(next, curr);
                }
                // Add nodes to the queue paired with their total weights
                if (!visited.contains(next)) {
                    unvisited.add(new DijikstraPair<T>(next, estimatedDistance.get(next)));
                    visited.add(next);
                }
            }
        }

        return (found) ? formatShortestPath(path, target) : new ArrayList<>();
    }

    private List<T> formatShortestPath(Map<T, T> path, T startKey) {
        List<T> out = new ArrayList<>();
        while (startKey != null) {
            out.add(startKey);
            startKey = path.get(startKey);
        }
        return out.reversed();
    }

    // Edge of graph - essentially a tuple of the weight of an edge and the destination node
    private static class WeightedEdge<T> {
        T destination;
        int weight;

        WeightedEdge(T destination, int weight) {
            this.destination = destination;
            this.weight = weight;
        }
    }

    // Tuple of the running sum weights at that point from the starting node and the current node itself
    // NOTE: looks simililar to weighted edge but represents a different thing
    private static class DijikstraPair<T> {
        T node;
        int distanceFromSource;

        DijikstraPair(T node, int distanceFromSource) {
            this.node = node;
            this.distanceFromSource = distanceFromSource;
        }
    }
}