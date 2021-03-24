import java.awt.*;
//import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Stop> stopList;
    private List<Edge> edgeList;
//    private Graphics g = new Graphics();
    private Color stopColour = Color.blue;
    private Color edgeColour = Color.pink;
    private Color highlightStop = Color.yellow;
    private Color highlightTrip = Color.orange;

    public Graph(List<Stop> stopList, List<Edge> edgeList) {
        this.stopList = stopList;
        this.edgeList = edgeList;

        // construct the adjacency lists
        for (Stop s : stopList) {
            s.adjListIncoming = new ArrayList<>();
            s.adjListOutgoing = new ArrayList<>();
        }
        for (Edge e : edgeList) {
            e.toStop.adjListIncoming.add(e);
            e.fromStop.adjListOutgoing.add(e);
        }
        // I haven't added the Stop neighbours and I don't need a list of outgoing edges because I have that in the stop itself
        for (Stop s : stopList) {
            for (Edge e : s.adjListOutgoing) {
                Edge outgoingEdge = e;
                Stop outgoingNeighbour = e.toStop;
            }
            for (Edge e : s.adjListIncoming) {
                Edge incomingEdge = e;
                Stop incomingNeighbour = e.fromStop;
            }
        }
    }
    // Graph requires a draw method
    public void draw(Graphics g, Location origin, double scale) {
        for (Stop s: stopList)
        {
            if(!s.isHighlighted){
                g.setColor(stopColour);
            }
            else {
                g.setColor(highlightStop);
            }
            s.draw(g, origin, scale);
        }
        for (Edge e : edgeList){
            if(!e.isHighlighted){
                g.setColor(edgeColour);
            }
            else{
                g.setColor(highlightTrip);
            }
            e.draw(g, origin, scale);
        }
    }
}
