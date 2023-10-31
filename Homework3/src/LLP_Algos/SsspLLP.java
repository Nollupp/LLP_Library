package LLP_Algos;

import InputOutputClasses.SSSPInput;
import InputOutputClasses.SSSPOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import Utility.Edge;
import Utility.LLPInterface;


public class SsspLLP extends LLPInterface 
{   
    // Inputs:
    Map<Integer, List<Integer>> pre; // For any vertex v, let pre(v) be the set of vertices u such that (u, v) is an edge in the graph. 
    int weights[][]; // Value in weights[i][j] is equal to weight of edge from vertex i to j
    int sourceVertex;
    int numVertices;

    // Extra variables: Parent array, fixed array, heap
    boolean parent[][]; // parent[j][i] is true if vertex i is a parent of vertex j
    boolean fixed[]; // Fixed[j] is equal to true if vertix j is fixed, otherwise false
    Map<Integer,PriorityQueue<Integer>> minHeap; 

    // Create the stable marriage LLP algo 
    public SsspLLP(SSSPInput input)
    {
        minHeap      = new HashMap<Integer,PriorityQueue<Integer>>();
        sourceVertex = input.startVertex;
        numVertices  = input.vertices;
        fixed        = new boolean[numVertices];
        weights      = new int[numVertices][numVertices];
        parent       = new boolean[numVertices][numVertices];
        pre          = new HashMap<Integer,List<Integer>>();

        // Create weights[][] amd pre(j)
        for (Edge edge : input.edgeList)
        {
            pre.computeIfAbsent(edge.end, k -> new ArrayList<>()).add(edge.start);
            weights[edge.start][edge.end] = edge.weight;
        }

        for (int i = 0; i < numVertices; i++)
        {
            minHeap.put(i,new PriorityQueue<>());
        }
    }
    
    public SSSPOutput runSsspLLP()
    {
        if (numVertices == 0 || (this.runAlgo(numVertices) == false))
        {
            System.out.println(Arrays.toString(this.GlobalState));
            return null;
        }
        else
        {
            SSSPOutput output = new SSSPOutput(this.GlobalState);
            return output;
        }
    }

    // ----------------------- LLP functionality -------------------------------------------------------

    @Override
    public void init(int vertex)
    {
        this.GlobalState[vertex] = 0;
    }

    @Override
    public void always(int j)
    {
        List<Integer> preOfJ = pre.get(j);
        boolean existsFixedParent = false;

        if (sourceVertex == j)
        {
            fixed[j] = true;
        }

        if (preOfJ == null) { return; }

        for (int i = 0; i < numVertices; i++)
        {
            parent[j][i] = preOfJ.contains(i) && (this.GlobalState[j] >= this.GlobalState[i] + weights[i][j]);
            
            if (parent[j][i] && fixed[i])
            {
                existsFixedParent = true;
            }
        }

        fixed[j] = (j == sourceVertex) || existsFixedParent;

        for (int k = 0; k < numVertices; k++)
        {
            List<Integer> preOfK = pre.get(k);

            if (preOfK == null) { continue;}

            for (Integer i : preOfK)
            {
                if (fixed[i] && !fixed[k])
                {
                    minHeap.get(j).add(this.GlobalState[i] + weights[i][k]);
                }
            }
        }
        
    }

    @Override
    public boolean forbidden(int currentVertex)  // This function decides whether a index is forbidden
    {
        return !fixed[currentVertex];
    }

    @Override
    public void advance(int j) 
    {
        List<Integer> preOfJ = pre.get(j);
        int minWeight1 = Integer.MAX_VALUE;
        
        if (preOfJ == null)
        {
            return;
        }
        
        for (Integer i : preOfJ)
        {
            if (this.GlobalState[i] + weights[i][j] < minWeight1)
            {
                minWeight1 = this.GlobalState[i] + weights[i][j];
            }
        }

        // Advance the forbidden index
        if (minHeap.get(j).isEmpty())
        {
            this.GlobalState[j] = minWeight1;
        }
        else
        {
            int minWeight2 = minHeap.get(j).poll();
            this.GlobalState[j] = Integer.max(minWeight1,minWeight2);
            minHeap.get(j).clear();
        }
    } 

    @Override
    public boolean ensure(int index) {
        // Not used in this LLP implemenation/algo
        return false;
    }
}
