package LLP_Algos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utility.Edge;

import InputOutputClasses.JAInput;
import InputOutputClasses.JAOutput;

public class JohnsonLLP extends LLPInterface
{

    // Inputs:
    Map<Integer, List<Integer>> pre; // For any vertex v, let pre(v) be the set of vertices u such that (u, v) is an edge in the graph. 
    int weights[][];                 // Value in weights[i][j] is equal to weight of edge from vertex i to j

    public JohnsonLLP(JAInput input)
    {
        weights      = new int[input.vertices][input.vertices];
        pre          = new HashMap<Integer,List<Integer>>();

        // Create weights[][] amd pre(j)
        for (Edge edge : input.edgeList)
        {
            pre.computeIfAbsent(edge.end, k -> new ArrayList<>()).add(edge.start);
            weights[edge.start][edge.end] = edge.weight;
        }

    }

    public JAOutput runJohnsonLLP()
    {
        // Run the price vector LLP algo first (to reweight the inputted graph)

        // Next, run the SSSP LLP algo with the reweighted graph as input

        // Convert back to original weights

        // TODO: This return value needs to actually be what we want to return
        return new JAOutput(new int[1][1]);
    }

    @Override
    public void init(int index) {
        // TODO: This needs to be filled out
    }

    @Override
    public void always(int index) {
         ; // Not used
    }

    @Override
    public void ensure(int index) {
        // TODO: This needs to be filled out
        ;
    }

    @Override
    public void advance(int index) {
        ; // Not used
    }

    @Override
    public boolean forbidden(int index) {
        return false; // Not used
    }
    
}
