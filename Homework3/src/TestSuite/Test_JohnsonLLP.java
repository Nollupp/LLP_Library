package TestSuite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import InputOutputClasses.JAInput;
import InputOutputClasses.JAOutput;
import LLP_Algos.JohnsonLLP;
import Utility.Edge;



public class Test_JohnsonLLP {
    public static void main(String[] args) throws Exception
    {
        // Test 1:
        List<Edge> edgeList = new ArrayList<>();

        // W = 0
        // X = 1
        // Y = 2
        // Z = 3

        int numVertices = 4;
    
        edgeList.add(new Edge(0, 3, 2)); // W to Z

        edgeList.add(new Edge(1, 0, 6)); // X to W
        edgeList.add(new Edge(1, 2, 3)); // X to Y

        edgeList.add(new Edge(2, 0, 4)); // Y to W
        edgeList.add(new Edge(2, 3, 5)); // Y to Z

        edgeList.add(new Edge(3, 1, -7)); // Z to X
        edgeList.add(new Edge(3, 2, -3)); // Z to Y

        JAInput input = new JAInput(4, edgeList.size(), 0, edgeList);
        JohnsonLLP JohnsonAlgo = new JohnsonLLP(input);

        JAOutput output = JohnsonAlgo.runJohnsonLLP();

        if (output == null)
        {
            System.out.println("No solution found!");
        }
        else
        {
            for (int i = 0; i < numVertices; i++)
            {
                System.out.println("Source vertex: " + i + " ---- Distance array: " + Arrays.toString(output.shortestDistances[i]));
            }
        }
        
    }
}
