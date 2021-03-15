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
        double xdistmin = 0;
        double xdistmax = 0;
        double ydistmin = 0;
        double ydistmax = 0;

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
                List<Trip> tripsthruStop = null;
                Stop stop = new Stop (stop_id, stop_name, stop_lat, stop_lon, stop_xy, adjListIncoming, adjListOutgoing, tripsthruStop);
                double xdisttemp = stop.stop_xy.x - Location.mapCentre_xy.x;
                double xdist = Math.abs(xdisttemp);
                if ( xdist > max_xdist) {
                    max_xdist = xdist;
                }
                if (xdisttemp > xdistmax) {
                    xdistmax = xdisttemp;
                }
                if (xdisttemp < xdistmin) {
                    xdistmin = xdisttemp;
                }
                double ydisttemp = stop.stop_xy.y - Location.mapCentre_xy.y;
                double ydist = Math.abs(ydisttemp);
                if (ydist > max_ydist) {
                    max_ydist = ydist;
                }
                if (ydisttemp > ydistmax) {
                    ydistmax = ydisttemp;
                }
                if (ydisttemp < ydistmin) {
                    ydistmin = ydisttemp;
                }

                stopsList.add(stop);
//                System.out.println("Stop " + stop.stop_name + stop.stop_xy);
                i++;
            }
        }
        System.out.println("Max x kms: " + max_xdist + " Max y kms: " + max_ydist);
        System.out.println("X kms from: " + xdistmin + " to: " + xdistmax + " Y kms: " + ydistmin + " to: " + ydistmax);
// so the map is centred on CENTRE MAP and is about ~40km wide and ~32km high
            // for an 800 x 800 pixel window that is a scale of 800/40 = 20 pixels per km
            //Zooming in to an area 20km wide would mean a scale of 800/20 = 40 pixels per km
            return stopsList;
    }
    public List<Trip> ReadTrips(File tripfile, List<Stop> stopsList) throws IOException {
        System.out.println("Started dataImport Trips Method");
        BufferedReader br = new BufferedReader(new FileReader(tripfile));
        String line = null;
        String[] stringLine;
        List<Trip> tripsList = new ArrayList<>();
        int i = 0;
        Stop firstStop = null;
        Stop lastStop = null;
        while(true){
            line = br.readLine();
            if(line == null){
                break;
            }
            // skip the header line for the trips data file
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
                List<Stop> tripSequence = new ArrayList<>();
//              Could add error message if the stop_id is not found in the stopsList or add unknown stop eg
//              Stop unknownStop = new Stop (null, "unknown",
//                        0,0, null, null, null);
                // the number of stops in the trip sequence is the length of the string minus the first item, the the trip_id
                int numStops = stringLine.length - 1;
//                System.out.println("Trip " + trip_id + " First Stop " + stringLine[1]  + " Last Stop " + stringLine[numStops] + " No. stops " + numStops);
                for (int j = 1; j < stringLine.length; j++){
                    //stringline[1] to stringLine[numStops] inclusive should be a stop id. If so, add that stop to the trip sequence
                    for (Stop s : stopsList){
                        if(stringLine[j].equals(s.stop_id)){
//                            System.out.println("s.stop_id = " + s.stop_id + " Stop name " + s.stop_name);
                            tripSequence.add(s);
                        }
                    }
                }
                // put that trip_id and Stop sequence into a Trip structure
                firstStop = tripSequence.get(0);
                lastStop = tripSequence.get(numStops-1);
                Trip trip = new Trip (trip_id, tripSequence, firstStop, lastStop);
//                System.out.println("On Line 114 ReadDataFiles Trip " + trip.trip_id + " First stop " + tripSequence.get(0).stop_name + " Last Stop " + tripSequence.get(numStops-1).stop_name + " No. stops " + numStops);
                tripsList.add(trip);
                i++;
            }
        }
//        System.out.println("On Line 121 ReadDataFiles Trip " + tripsList.get(100).trip_id + " TripList size " + tripsList.get(100).tripSequence.size()); // + " Last Stop " + tripSequence.get(numStops-1).stop_name + " No. stops " + numStops);
        return tripsList;
    }
}
