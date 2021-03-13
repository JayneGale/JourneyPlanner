import java.util.List;
import java.util.ArrayList;

public class MakeMap {
    public Trip trip;
    public Stop stop;
    public List<Edge> edgeList = new ArrayList();
    public List<Edge> CreateEdgeList(List<Trip> tripsList) {
        edgeList.clear();
// creates the Edge list from the Trip sequences
        for (Trip t : tripsList){
            List<Stop> stops = t.tripSequence;
            int numStops = t.tripSequence.size();
            List<Edge> tripEdges = new ArrayList();
            for (int i = 0; i < numStops; i++){
                Stop fromStop = stops.get(i);
                if (i >= numStops){

                    Stop toStop = stops.get(i+1);
                Edge e = new Edge(fromStop, toStop);
                if (!tripEdges.contains(e)){
                    tripEdges.add(e);
                    }

                }

            }
        }
//                if (edgeList contains stops[i]
        return edgeList;
    }
    public Graph CreateGraph(List<Stop> stops, List<Edge> edges){
        Graph g = new Graph(stops, edges);
        return g;

    }
}
