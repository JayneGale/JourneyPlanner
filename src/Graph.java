import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Stop> stops;
    private List<Edge> edges;

    public Graph(List<Stop> stops, List<Edge> edges) {
        this.stops = stops;
        this.edges = edges;

        // construct the adjacency lists
        for (Stop stop : stops) {
            stop.adjListIncoming = new ArrayList<>();
            stop.adjListOutgoing = new ArrayList<>();
        }
        for (Edge edge : edges) {
            edge.toStop.adjListIncoming.add(edge);
            edge.fromStop.adjListOutgoing.add(edge);
        }

        for (Stop stop : stops) {
            for (Edge edge : stop.adjListOutgoing) {
                Edge outgoingEdge = edge;
                Stop outgoingNeighbour = edge.toStop;
            }
            for (Edge edge : stop.adjListIncoming) {
                Edge incomingEdge = edge;
                Stop incomingNeighbour = edge.fromStop;
            }
        }
//// Graph requires a draw method
//        public void DrawGraph () {
//            drawGraph();
//        }
//
//    }
//
//    private void drawGraph() {
//    }
    }
}
