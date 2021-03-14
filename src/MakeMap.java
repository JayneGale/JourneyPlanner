import java.util.List;
import java.util.ArrayList;

public class MakeMap {
    public Trip trip;
    public Stop stop;
    public Edge e;
    public List<Edge> edgeList = new ArrayList();

    // this creates a unique list of all the Edges on the Map from the Trip sequences
    // TO DO: run through each trip's tripSequence and create the edge files for each stop in the trip sequence
    //  s.adjListIncoming.add()
    //  s.adjListOutgoing.add()
    //  fromStop = tripSequence j - 1
    //  toStop = tripSequence j + 1)
    //  Edge edge = new Edge (edge_id, fromStop, toStop)

    public List<Edge> CreateEdgeList(List<Trip> tripsList) {
        edgeList.clear();
        for (Trip t : tripsList) {
            List<Stop> stopSequence = t.tripSequence;
            int numStops = t.tripSequence.size();
            Stop fromStop;
            Stop toStop;
            Edge e;
//            System.out.println("CreateEdgeList MakeMap line 26 for Trip " + t.trip_id + " stopSequence size " + numStops);
            //skip the first and last Stops in this trip's stop sequence tripSequence,
            // index (numStops) - 1) has no toStop hence no Edge
            //add all the middle stops s(0 to s(numStops - 2)) if there are any
            // if there isonly one Stop there is no edge for either stop
            if (numStops > 1) {
                for (int i = 0; i < numStops - 1; i++) {
                    fromStop = stopSequence.get(i);
                    toStop = stopSequence.get(i + 1);
                    e = new Edge(fromStop, toStop);
                    if (i == 0){
//                        System.out.println(" MakeMap 37 new Edge for Stop " + e.fromStop.stop_name + " to Stop" + e.toStop.stop_name);
                    }
                   edgeList = AddTripEdge(e, edgeList);
                }
            }
        }
        int numEdges = edgeList.size();
        System.out.println("ln 44 MakeMap CreateEdgeList finished, there are " + numEdges + "edges in the list.");
        if (numEdges > 0)
                System.out.println("Ln 46 makeMap the First Edge is from " + edgeList.get(0).fromStop.stop_name + " to " + edgeList.get(0).fromStop.stop_name + ". The Last Edge is from " + edgeList.get(numEdges-1).fromStop.stop_name + " to " + edgeList.get(numEdges-1).toStop.stop_name) ;
        return edgeList;
    }

    public Graph CreateGraph(List<Stop> stops, List<Edge> edges) {
        Graph g = new Graph(stops, edges);
        System.out.println("CreateGraph started ");
//        for (Stop s : stops) {
//            List<Edge> adjListIn = s.adjListIncoming;
//            List <Edge> adjListOut = s.adjListOutgoing;
//        }
//        System.out.println("CreateGrapgh started " + g.stops);
        return g;

    }

    private List<Edge> AddTripEdge(Edge e, List<Edge> edgeList) {
        if (!edgeList.contains(e)) {
//            System.out.println("AddTripEdge ln 64 MakeMap Adding new Edge from Stop " + e.fromStop.stop_name + " to Stop " + e.toStop.stop_name);
            edgeList.add(e);
        }
        return edgeList;
    }
// Put these into draw() But where to put draw()
//        double scale;
//        double screenpixel_u = JourneyPlanner.getDrawingAreaDimension().getWidth();
//        double screenpixel_v= JourneyPlanner.getDrawingAreaDimension().getHeight();
//        double scale_x = screenpixel_u/max_xdist;
//        double scale_y = screenpixel_v/max_ydist;
//        scale = Math.min(scale_x, scale_y);
//        System.out.println("scale " + scale + " scale_x " + scale_x + " Scale y " + scale_y);
}
