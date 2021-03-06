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

    public List<Edge> CreateEdgeList(List<Trip> tripsList, List<Stop> stopslist) {
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
                    //I could put this in here when generating the edgelist, but this list repeats so  have to return 2 values ie the Graph, so do this later from the final edgeList
//                   fromStop.adjListOutgoing.add(e);
//                   toStop.adjListIncoming.add(e);
                }
            }
        }
        return edgeList;
    }

    public Graph CreateGraph(List<Stop> stopsList, List<Edge> edgeList) {
        Graph gr = new Graph(stopsList, edgeList);
        System.out.println("CreateGraph started ");

        for (Stop s : stopsList) {
            List<Edge> adjListIn = s.adjListIncoming;
            List <Edge> adjListOut = s.adjListOutgoing;
        }
        System.out.println("CreateGraph ln 63 " + stopsList.get(0).adjListIncoming.isEmpty());
        return gr;
    }
    private List<Edge> AddTripEdge(Edge e, List<Edge> edgeList) {
        if (!edgeList.contains(e)) {
//            System.out.println("AddTripEdge ln 64 MakeMap Adding new Edge from Stop " + e.fromStop.stop_name + " to Stop " + e.toStop.stop_name);
            edgeList.add(e);
        }
        return edgeList;
    }
}
