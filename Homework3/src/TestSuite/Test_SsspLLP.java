package TestSuite;

import LLP_Algos.SsspLLP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import InputOutputClasses.SSSPInput;
import InputOutputClasses.SSSPOutput;
import Utility.Edge;

enum Vertex {
    A,
    B,
    C,
    D,
    E,
    F,
    G
}

public class Test_SsspLLP {
    public static void main(String[] args) throws Exception 
    {

        // Test 1:
        List<Edge> edgeList = new ArrayList<>();

        edgeList.add(new Edge(Vertex.A.ordinal(), Vertex.B.ordinal(), 1)); // A to B
        edgeList.add(new Edge(Vertex.A.ordinal(), Vertex.C.ordinal(), 5)); // A to C

        edgeList.add(new Edge(Vertex.B.ordinal(), Vertex.C.ordinal(), 2)); // B to C
        edgeList.add(new Edge(Vertex.B.ordinal(), Vertex.D.ordinal(), 2)); // B to D
        edgeList.add(new Edge(Vertex.B.ordinal(), Vertex.E.ordinal(), 1)); // B to E

        edgeList.add(new Edge(Vertex.C.ordinal(), Vertex.E.ordinal(), 2)); // C to E

        edgeList.add(new Edge(Vertex.D.ordinal(), Vertex.E.ordinal(), 3)); // D to E
        edgeList.add(new Edge(Vertex.D.ordinal(), Vertex.F.ordinal(), 1)); // D to F

        edgeList.add(new Edge(Vertex.E.ordinal(), Vertex.F.ordinal(), 2)); // E to F

        SSSPInput input = new SSSPInput(6, 9, 0, edgeList);
        SsspLLP SsspAlgo = new SsspLLP(input);

        SSSPOutput output = SsspAlgo.runSsspLLP();

        if (output == null)
        {
            System.out.println("No solution found!");
        }
        else
        {
            System.out.println(Arrays.toString(output.shortestDistances));
        }



        // Test 2

        edgeList = new ArrayList<>();

        edgeList.add(new Edge(0, 1, 2)); // A to B
        edgeList.add(new Edge(0, 2, 6)); // A to C

        edgeList.add(new Edge(1, 3, 5)); // B to C

        edgeList.add(new Edge(2, 3, 8)); // B to D

        edgeList.add(new Edge(3, 5, 15)); // B to E
        edgeList.add(new Edge(3, 4, 10)); // B to E

        edgeList.add(new Edge(4, 5, 6)); // C to E
        edgeList.add(new Edge(4, 6, 2)); // C to E

        edgeList.add(new Edge(5, 6, 6)); // C to E

        input = new SSSPInput(7, 9, 0, edgeList);
        SsspAlgo = new SsspLLP(input);

        output = SsspAlgo.runSsspLLP();

        if (output == null)
        {
            System.out.println("No solution found!");
        }
        else
        {
            System.out.println(Arrays.toString(output.shortestDistances));
        }
    }
}
