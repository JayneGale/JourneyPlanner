import java.util.List;

public class Trip {
    //    attributes could be eg: time, distance, cost, bus number
    //    TO DO create List Map Edges with attributes toStop and fromStop

    String trip_id;
    List<Stop> tripSequence; // a List of Stop objects
    Stop firstStop;
    Stop lastStop;
    String name;
    public boolean isHighlightedTrip = false;


    // A trip is a sequence of Stops for a bus. It's normal for a Bus to display its final Stop (lastStop) to identify it to potential users of the Trip
    public Trip(String trip_id, List<Stop> tripSequence, Stop firstStop, Stop lastStop){ //, Stop fromStop, Stop toStop)
        this.trip_id= trip_id;
        this.tripSequence = tripSequence;
        this.firstStop = firstStop;
        this.lastStop = lastStop;
        name = firstStop.stop_name + " to " + lastStop.stop_name;
    }
}

