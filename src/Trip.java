import java.util.List;

public class Trip {
    //    attributes could be eg: time, distance, cost, bus number
    //    TO DO create List Map Edges with attributes toStop and fromStop

    String trip_id;
    List tripSequence;
    // pseudocod. If there is a stop sequence from Stop A to Stop B, create a fromNode for AB
    //    Stop fromStop;
    //    Stop toStop;

    public Trip(String trip_id, List tripSequence){ //, Stop fromStop, Stop toStop)
        this.trip_id= trip_id;
        this.tripSequence = tripSequence;
//        this.fromStop = fromStop;
//        this.toStop = toStop;
    }
//    for(Stop s : tripSequence){

}

