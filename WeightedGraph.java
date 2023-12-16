
import java.util.*;

class Edge<T> {
    T connectedVetex; //connected vertex
    int weight; //weight

    //Constructor, Time O(1) Space O(1)
    public Edge(T v, int w) {
        this.connectedVetex = v;
        this.weight = w;
    }

    //Time O(1) Space O(1)
    @Override
    public String toString() {
        return "(" + connectedVetex + "," + weight + ")";
    }
}

public class WeightedGraph<T> {
    Map<T, LinkedList<Edge<T>>> adj = new HashMap<>() ;
    //Default Constructor, Time O(1) Space O(1)
    public WeightedGraph () {
    }

    //Add edges including adding nodes, Time O(1) Space O(1)
    public void addEdge(T a, T b, int w) {
        adj.putIfAbsent(a, new LinkedList<>()); //add node
        adj.putIfAbsent(b, new LinkedList<>()); //add node
        Edge<T> edge1 = new Edge<>(b, w);
        adj.get(a).add(edge1); //add edge
        // as undirected if C1 is connected to C2 then C2 is also connected to C1-->Two way traffic
            Edge<T> edge2 = new Edge<>(a, w);
            adj.get(b).add(edge2);

    }

    //Find the edge between two nodes, Time O(n) Space O(1), n is number of neighbors
    private Edge<T> findEdgeByVetex(T a, T b) {
        LinkedList<Edge<T>> ne = adj.get(a);
        for (Edge<T> edge: ne) {
            if (edge.connectedVetex.equals(b)) {
                return edge;
            }
        }
        return null;
    }

    //Check whether there is node by its key, Time O(1) Space O(1)
    public boolean hasNode(T key) {
        return adj.containsKey(key);
    }

    //Check whether there is direct connection between two nodes, Time O(n), Space O(1)
    public boolean hasEdge(T a, T b) {
        Edge<T> edge1 = findEdgeByVetex(a, b);
         //undirected or bi-directed
            Edge<T> edge2 = findEdgeByVetex(b, a);
            return edge1 != null && edge2!= null;
    }

    //Check there is path from src and dest
    //DFS, Time O(V+E), Space O(V)
    public boolean hasPathDFS(T src, T dest) {
        HashMap<T, Boolean> visited = new HashMap<>();
        return dfsHelper(src, dest, visited);
    }

    //DFS helper, Time O(V+E), Space O(V)
    private boolean dfsHelper(T v, T dest, HashMap<T, Boolean> visited) {
        if (v == dest)
            return true;
        visited.put(v, true);
        for (Edge<T> edge : adj.get(v)) {
            T u = edge.connectedVetex;
            if (visited.get(u) == null)
                return dfsHelper(u, dest, visited);
        }
        return false;
    }

    //Print graph as hashmap, Time O(V+E), Space O(1)
    public void printGraph() {
        for (T key: adj.keySet()) {
            System.out.println(key + "," + adj.get(key));
        }
    }

    //Traversal starting from src, DFS, Time O(V+E), Space O(V)
    public void dfsTraversal(T src) {
        HashMap<T, Boolean> visited = new HashMap<>();
        helper(src, visited);
        System.out.println();
    }

    //DFS helper, Time O(V+E), Space O(V)
    private void helper(T v, HashMap<T, Boolean> visited) {
        visited.put(v, true);
        System.out.print(v.toString() + " ");
        for (Edge<T> edge : adj.get(v)) {
            T u = edge.connectedVetex;
            if (visited.get(u) == null)
                helper(u, visited);
        }
    }

    public static void main(String[] args) {
		/*
		 Test case 1, Undirected graph
		 1-- 3
		 | \ |
		 2   4
		*/
        WeightedGraph<Integer> g = new WeightedGraph<>();
        g.addEdge(1, 2, 26);
        g.addEdge(1, 3, 13);
        g.addEdge(1, 4, 28);
        g.addEdge(3, 4, 19);
        System.out.println("undirected graph:");
        g.printGraph();
        System.out.println("dfs:");
        g.dfsTraversal(1);
        System.out.println("has path 2,3 (DFS): " + g.hasPathDFS(2, 3));
        System.out.println("has path 2,5 (DFS): " + g.hasPathDFS(2, 5));

    }
}
