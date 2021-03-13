import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.Math;

public class ReadDataFiles {

        public List<Stop> ReadStops(File stopfile) throws IOException {
        //            System.out.println("Started dataImport Stops Method");
        BufferedReader br = new BufferedReader(new FileReader(stopfile));
        String line;
        double stop_lat;
        double stop_lon;
        String[] stringLine;
        List<Stop> stopsList = new ArrayList<>();
        int i = 0;
        double max_xdist = 0;
        double max_ydist = 0;

            while(true){
                line = br.readLine();
                if(line == null){
                    break;
                }
            // skip the header line
            if(i == 0){
                stopsList.clear();
                i++;
            }
            else{
                // split the data String on tabs
                stringLine = line.split("\t");
                String stop_id = stringLine[0];
                String stop_name = stringLine[1];
                stop_lat = Double.parseDouble(stringLine[2]);
                stop_lon = Double.parseDouble(stringLine[3]);
                Location stop_xy = Location.newFromLatLon(stop_lat, stop_lon);
                List<Edge> adjListIncoming = null;
                List<Edge> adjListOutgoing = null;
                Stop stop = new Stop (stop_id, stop_name, stop_lat, stop_lon, stop_xy, adjListIncoming, adjListOutgoing);

                    double xdist = Math.abs(stop.stop_xy.x - Location.mapCentre_xy.x);
                if ( xdist > max_xdist) {
                    max_xdist = xdist;
                }
                double ydist = Math.abs(stop.stop_xy.y - Location.mapCentre_xy.y);
                if ( ydist > max_ydist) {
                    max_ydist = ydist;
                }

                stopsList.add(stop);
                        System.out.println("Stop " + stop.stop_name + stop.stop_xy);
    //                    System.out.println("Stop " + stop.stop_name + stop.stop_id + stop.stopxy);
                i++;
            }
        }
        System.out.println("Max x kms: " + max_xdist + " Max y kms: " + max_ydist);
//        double scale;
//        double screenpixel_u = JourneyPlanner.getDrawingAreaDimension().getWidth();
//        double screenpixel_v= JourneyPlanner.getDrawingAreaDimension().getHeight();
//        double scale_x = screenpixel_u/max_xdist;
//        double scale_y = screenpixel_v/max_ydist;
//        scale = Math.min(scale_x, scale_y);
//        System.out.println("scale " + scale + " scale_x " + scale_x + " Scale y " + scale_y);
        return stopsList;
    }
    public List<Trip> ReadTrips(File tripfile, List<Stop> stopsList) throws IOException {
        System.out.println("Started dataImport Trips Method");
        BufferedReader br = new BufferedReader(new FileReader(tripfile));
        String line = null;
        String[] stringLine;
        List<Trip> tripsList = new ArrayList<>();
        int i = 0;
        while(true){
            line = br.readLine();
            if(line == null){
                break;
            }
            // skip the header line
            if(i == 0){
                //start each time with an empty tripsList
                tripsList.clear();
                i++;
            }
            else {
//                System.out.println("Line " + i);
                stringLine = line.split("\t");
                String trip_id = stringLine[0];
//               create a list of Stops from this String sequence of stop_ids for this trip_id
                List<Stop> tripSequence = new ArrayList();
//                Stop unknownStop = new Stop (null, "unknown",
//                        0,0, null, null, null);
                // the number of stops in the trip sequence is the length of the string minus the first item, the the trip_id
                int numStops = stringLine.length - 1;
                System.out.println("Trip " + trip_id + " First Stop " + stringLine[1]  + " Last Stop " + stringLine[numStops] + " No. stops " + numStops);
                for (int j = 1; j < stringLine.length; j++){
                    //stringline[1] to stringLine[numStops] inclusive should be a stop id. If so, add that stop to the trip sequence
                    for (Stop s : stopsList){
                        if(stringLine[j].equals(s.stop_id)){
                            System.out.println("s.stop_id = " + s.stop_id + " Stop name " + s.stop_name);
                            tripSequence.add(s);
                            System.out.println("s.Stop name " + s.stop_name);
                        }
                    }
                }
                // TO DO: now run through the tripSequence and create the edge files for each stop in the trip sequence 
                //  s.adjListIncoming.add()
                //  s.adjListOutgoing.add()
                //  fromStop = tripSequence j - 1
                //  toStop = tripSequence j + 1)
                //  Edge edge = new Edge (edge_id, fromStop, toStop)

                System.out.println("Trip " + trip_id + " First stop " + tripSequence.get(0).stop_name + " Last Stop " + tripSequence.get(numStops-1).stop_name + " No. stops " + numStops);
                // put that trip_id and Stop sequence into a Trip structure
                Trip trip = new Trip (trip_id, tripSequence);
                tripsList.add(trip);
                // now set up for the next line by clearing out the list
                tripSequence.clear();
//                System.out.println("Trip " + trip.trip_id + trip.stopSequence);
                i++;
            }
        }
        return tripsList;
    }

}
