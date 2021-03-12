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
    public List<Trip> ReadTrips(File tripfile) throws IOException {
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
                tripsList.clear();
                i++;
            }
            else {
//                System.out.println("Line " + i);
                stringLine = line.split("\t");
                String trip_id = stringLine[0];
                List tripSequence = new ArrayList();
                int numStops = stringLine.length - 1;
                System.out.println("Trip " + trip_id + " First Stop " + stringLine[1]  + " Last Stop " + stringLine[numStops] + " No. stops " + numStops);
                for (int j = 1; j < stringLine.length; j++){
//                    if (i == 1) {
//                        System.out.println("stop " + j + ": " + stringLine[j]);
//                    }
                    tripSequence.add(stringLine[j]);
                }
                Trip trip = new Trip (trip_id, tripSequence);
                tripsList.add(trip);
                tripSequence.clear();
//                System.out.println("Trip " + trip.trip_id + trip.stopSequence);
                i++;
            }
        }
        return tripsList;
    }

}
