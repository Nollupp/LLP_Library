package LLP_Algos;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

class Edge {
    public int start, end, weight;

    public Edge(int start, int end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
}

public class SsspLLP extends LLPInterface 
{   
    // Inputs:
    Map<Integer, List<Integer>> pre; // For any vertex v, let pre(v) be the set of vertices u such that (u, v) is an edge in the graph. 
    int weights[][]; // Value in weights[i][j] is equal to weight of edge from vertex i to j

    // Extra variables: Parent array, fixed array, heap
    int parents[][]; // TODO:Nolan, Figure this out
    boolean fixed[]; // Fixed[j] is equal to true if vertix j is fixed, otherwise false
    PriorityQueue<Integer> minHeap; // TODO:Nolan, figure out what this holds

    // Create the stable marriage LLP algo 
    public SsspLLP(int vertices, int edges, int startVertex, List<Edge> edgeList)
    {
        minHeap = new PriorityQueue<Integer>();
        ;
    }
    
    public void runSsspLLP()
    {
        this.runAlgo(0);
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void printGlobalState()
    {
        System.out.println(Arrays.toString(this.GlobalState));
    }

    @Override
    public void init(int vertex)
    {
        this.GlobalState[vertex] = 0;
    }

    @Override
    public void always(int currentMan)
    {
        ;
    }

    @Override
    public boolean forbidden(int currentMan)  // This function decides whether a index is forbidden
    {
        return false;
    }

    @Override
    public void advance(int currentMan) 
    {
        // Advance the forbidden index
        ;
    }

    @Override
    public void ensure(int index) {
        // Not used in this LLP implemenation/algo
        ;
    }
}
