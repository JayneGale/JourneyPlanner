import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;

        // construct the adjacency lists
        for (Node node : nodes) {
            node.adjListIncoming = new ArrayList<>();
            node.adjListOutgoing = new ArrayList<>();
        }
        for (Edge edge : edges) {
            edge.toNode.adjListIncoming.add(edge);
            edge.fromNode.adjListOutgoing.add(edge);
        }

        for (Node node : nodes) {
            for (Edge edge : node.adjListOutgoing) {
                Edge outgoingEdge = edge;
                Node outgoingNeighbour = edge.toNode;
            }
            for (Edge edge : node.adjListIncoming) {
                Edge incomingEdge = edge;
                Node incomingNeighbour = edge.fromNode;
            }
        }

//        public void DrawMGraph() {
//            drawGraph();
//        }

    }
    // Graph requires a draw method

    private void drawGraph() {

    }
}
