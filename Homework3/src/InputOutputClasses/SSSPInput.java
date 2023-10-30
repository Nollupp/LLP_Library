package InputOutputClasses;

import java.util.List;

import Utility.Edge;

public class SSSPInput {
    public int vertices; // Number of vertices
    public int edges; // Number of edges
    public int startVertex;
    public List<Edge> edgeList;

    public SSSPInput(int vertices, int edges, int startVertex, List<Edge> edgeList) {
        this.vertices = vertices;
        this.edges = edges;
        this.startVertex = startVertex;
        this.edgeList = edgeList;
    }
}
