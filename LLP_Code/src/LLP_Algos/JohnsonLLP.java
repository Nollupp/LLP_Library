package LLP_Algos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utility.Edge;
import Utility.LLPInterface;
import InputOutputClasses.JAInput;
import InputOutputClasses.JAOutput;
import InputOutputClasses.SSSPInput;
import InputOutputClasses.SSSPOutput;

public class JohnsonLLP extends LLPInterface
{

    // Inputs:
    Map<Integer, List<Integer>> pre; // For any vertex v, let pre(v) be the set of vertices u such that (u, v) is an edge in the graph. 
    int weights[][];                 // Value in weights[i][j] is equal to weight of edge from vertex i to j
    int sourceVertex;
    int numVertices;
    int numEdges;
    List<Edge> edgeList;

    public JohnsonLLP(JAInput input)
    {
        weights      = new int[input.vertices][input.vertices];
        pre          = new HashMap<Integer,List<Integer>>();
        sourceVertex = input.startVertex;
        numVertices  = input.vertices;
        numEdges     = input.edges;
        edgeList     = input.edgeList;

        // Create weights[][] amd pre(j)
        for (Edge edge : input.edgeList)
        {
            pre.computeIfAbsent(edge.end, k -> new ArrayList<>()).add(edge.start);
            weights[edge.start][edge.end] = edge.weight;
        }

    }

    public JAOutput runJohnsonLLP()
    {
        // Run the price vector LLP algo first to get the vertice prices:
        if (numVertices == 0 || (this.runAlgo(numVertices) == false))
        {
            System.out.println(Arrays.toString(this.GlobalState));
            return null;
        }

        // Once we have the price vector, lets change the weights using this formula
        // weights[u][v] = w[u][v] + (-1)*h(u) - (-1)*h(v)
        for (int u = 0; u < numVertices; u++)
        {
            for (int v = 0; v < numVertices; v++)
            {
                weights[u][v] = weights[u][v] + ((-1)*GlobalState[u]) - ((-1)*GlobalState[v]);
            }
        }
        
        // Reweight edges: for all the edges in edgelist, edge.weight = weight[edge.start][edge.end]
        for (Edge edge : edgeList)
        {
            edge.weight = weights[edge.start][edge.end];
            System.out.println("From " + edge.start + " to " + edge.end + " : " + edge.weight);
        }

        // Next, run the SSSP LLP algo on each vertice, with the reweighted graph as input

        int tempOutput[][] = new int[numVertices][numVertices];

        for (int vertexElement = 0; vertexElement < numVertices; vertexElement++)
        {
            SSSPInput input = new SSSPInput(numVertices, numEdges, vertexElement, edgeList);
            SsspLLP SsspAlgo = new SsspLLP(input);

            SSSPOutput SSSPoutput = SsspAlgo.runSsspLLP();

            tempOutput[vertexElement] = SSSPoutput.shortestDistances;
        }

        // Convert back to original weights by using backwards formula:
        // weights[u][v] = w[u][v] + h(u) - h(v)

        for (int u = 0; u < numVertices; u++)
        {
            for (int v = 0; v < numVertices; v++)
            {
                tempOutput[u][v] = tempOutput[u][v] + (GlobalState[u]) - (GlobalState[v]);
            }
        }

        return new JAOutput(tempOutput);
    }

    @Override
    public void init(int index) 
    {
        this.GlobalState[index] = 0;
    }

    @Override
    public void always(int index) {
         ; // Not used
    }

    @Override
    public boolean ensure(int index) {

        if (pre.get(index) == null)
        {
            return false;
        }

        int max = Integer.MIN_VALUE;
        for (Integer i : pre.get(index))
        {
            if (max < this.GlobalState[i] - weights[i][index])
            {
                max = this.GlobalState[i] - weights[i][index];
            }
        }
        
        if (this.GlobalState[index] < max)
        {
            this.GlobalState[index] = max;
            return true;
        }
        
        return false; // We didn't need to change index to ensure this expression was true
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
