package InputOutputClasses;

import Utility.Edge;
import java.util.List;

public class JAInput {
    public int vertices; // Number of vertices
    public int edges; // Number of edges
    public int startVertex;
    public List<Edge> edgeList;

    public JAInput(int vertices, int edges, int startVertex, List<Edge> edgeList) {
        this.vertices = vertices;
        this.edges = edges;
        this.startVertex = startVertex;
        this.edgeList = edgeList;
    }
}