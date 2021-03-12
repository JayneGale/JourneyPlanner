import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.awt.Point;

public class DrawStops {
}

        //    private static final int NUM_SQUARES = 10;
//        private static final int STOPMARKER_SIZE = 16;
//        private final List<Stop> stopsList = new ArrayList<>();
//        private final List<StopMarker> stopMarkers = new ArrayList<>();
        //not sure I need 2 lists but let's try it, as Stop Marker is much simpler than Stop, just a place marker in x, y space
//        private final List<Trip> tripsList = new ArrayList<>();

//    public DrawStops() {
//        makeMap();
//    }
//
//    private void makeMap() { //this is a constructor for a new MakeMap method
//          first clear the map of bus stop marker points
//        stopMarkers.clear();
        //TO DO find all the stops that are visible in the window area (this may not be necessary)
        // for now, add all the stops
//        for (Stop s : stopsList){
//            StopMarker sm = new StopMarker(s.stop_xy, getDrawingAreaDimension());
//            stopMarkers.add(sm);
//        }
//        System.out.println("makeMap method called");
//        getTextOutputArea().setText("new Stops map created.");
//    }


//defined in main class and then passed        //draw method


//        g.drawRect stop_xy.asPoint.x;
//        same for .y
//        then do size 4,4)

        // lat and lon not needed to be an attribute of the Stop object as I translate this into x y space when I initalise (read in Stop data file)
        // or no,is it needed for the newfromLatLon method? or should I create a new method for newfromxy?
        // pixel coordinates will change on redraw so does not belong to the Stop object
        //TO DO add the adjacency lists
//            public List<Trip> adjListIncoming; //is the adjacency list of this node each node has a list of adjacent nodes
//            public List<Trip> adjListOutGoing; // and declare the same for outgoing nodes

//    public class Map {
//            public List<Stop> stops;
//            public List<Trip> trips;
//
//            public Map(List<Stop> stops, List<Trip> trips) {
//                Map map; // = new Map();
//                this.stops = stops;
//                this.trips = trips;
//            }
//
//            protected List<Trip> undirectedTrip; //= new List<Edge>();
//            //[nodes.size()][nodes.size()];
//        }

//        public List<Stop> dataImportStops(File stopfile) throws IOException {

//    public List<Trip> dataImportTrips(File tripfile) throws IOException {
//        System.out.println("Started dataImport Trips Method");
//        BufferedReader br = new BufferedReader(new FileReader(tripfile));
//        String line = null;
//        String[] stringLine;
//        Trip trip = new Trip();
//        int i = 0;
//
//        while(true){
//            line = br.readLine();
//            if(line == null){
//
//                break;
//            }
//            // skip the header line
//            if(i == 0){
//                tripsList.clear();
//                i++;
//                continue;
//            }
//            else{
////                System.out.println("Line " + i);
//                stringLine = line.split("\t");
//                trip.trip_id = stringLine[0];
////                int numStops = stringLine.length - 1;
////                System.out.println("Trip " + trip.trip_id + " First Stop " + stringLine[1]  + " Last Stop " + stringLine[numStops] + " No. stops " + numStops);
//                trip.stopSequence.clear();
//                for (int j = 1; j < stringLine.length; j++){
////                    if (i == 1) {
////                        System.out.println("stop " + j + ": " + stringLine[j]);
////                    }
//                    trip.stopSequence.add(stringLine[j]);
//                }
//                tripsList.add(trip);
////                System.out.println("Trip " + trip.trip_id + trip.stopSequence);
//                i++;
//            }
//        }
//        return tripsList;
//    }

//        private static class StopMarker {
//            public final Color color = Color.cyan;
//            public final int x = 0;
//            public final int y = 0;
//            I want to draw the stopmarkers at stop_xy toPoint
//            public StopMarker(Location stop_xy, Dimension area) {
//                Point point = Location.mapCentre_xy.asPoint();
//                Point stoppoint = stop_xy.asPoint(origin, scale);

//                Point sm_pix = new Point();
//                double scale = 1; //hmm fix this
//                Location origin = Location.mapCentre_xy;
//
//                public Point asPoint(Location.mapCentre_xy, scale) {
//                    int u = (int) ((x - origin.x) * scale);
//                    int v = (int) ((origin.y - y) * scale);
//                    return new Point(u, v);

//                }

//                sm_pix = Location.mapCentre_xy.asPoint();
//                x = (int)stop_xy.x;
//                y = (int)stop_xy.y;
//            public void draw(Graphics g) {
//
//                g.setColor(color);
//                g.fillRect(x, y, STOPMARKER_SIZE, STOPMARKER_SIZE);
//            }
//        public boolean contains(int x, int y) {
//            return x > this.x && y > this.y && x < this.x + SQUARE_SIZE
//                && y < this.y + SQUARE_SIZE;
//        }

// code for COMP261 assignments
