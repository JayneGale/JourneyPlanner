import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.lang.Math;

import static java.lang.Math.min;

public class DrawStops extends GUI {
    /**
     * Create a new Class
     * public class Graph{
     *     private List<Node> nodes;
     *  private List<Edge> edges;
     *  { this,nodes = nodes
     *      this.edges = edges
     *
     *    private List<> adjList = new int[nodes.size()]nodes.size()];]
     * }
     * public Graph(List<Node> nodes, List<> Edge) edges {
     *     this.
     * adjMatrix initialise to false
     *
     * for (int  < nodes.size();
     * for j < nodes size()
     * adjMatrix[i][j] = -1;
     *
     * in the Node Class
     * public List<Edge> adjListIn; is the adjacency list of this node each node has a list of adjacent nodes
     * and declare the same for outgoing nodes
     * for (Node node : nodes) {
     *     node.adjListIn = new Arraylist <>();
     *     initialising both
     *     }
     *     now enumerate all the edges from the node
     *     for Edge edge : edges){
     *         edge.fromNode.adjListIOut add(edge.toNode) add the toNode for each node
     *         r
     *         Now create a separate outgoing
     * public List<Node> getNodes() {
     *     return nodes;
     *     }
     *
     *     same for Edge
     *     the List has to be a List of Edges so we can get all the Edge information
     *
     * author jayne
     */

        //    private static final int NUM_SQUARES = 10;
        private static final int SQUARE_SIZE = 10;
        private final List<Stop> stopsList = new ArrayList<Stop>();
        private final List<StopMarker> stopMarkers = new ArrayList<StopMarker>();
        //not sure I need 2 lists but let's try it, as Stop Marker is much simpler than Stop, just a place marker in x, y space
        private final List<Trip> tripsList = new ArrayList<Trip>();

    public DrawStops() {
        makeMap();
    }

    private void makeMap() { //this is a constructor for a new MakeMap method
//          first clear the map of bus stop marker points
        stopMarkers.clear();
        //TO DO find all the stops that are visible in the window area (this may not be necessary)
        // for now, add all the stops
        for (Stop s : stopsList){
            StopMarker sm = new StopMarker(s.stop_xy);
            stopMarkers.add(sm);
        }
        System.out.println("makeMap method called");
        getTextOutputArea().setText("new Stops map created.");
    }

    public class Trip {
        public String trip_id;
        public List<String> stopSequence = new ArrayList<String>();
        //attributes could be eg: time, distance, start and end nodes
    }

    public class Stop {
        public String stop_id;
        public String stop_name;
        public Location stop_xy;
        public double max_xdist = 0;
        public double max_ydist = 0;
        // not needed to be an attribute of the Stop object as I translate this into x y space when I read in the data file
        // and the pixel coordinates will change on redraw so does not belong to the object
        // public double stop_lat; //x coordinates of the latitude coordinates X in window space
        // public double stop_lon; //y coordinates of the longitude Y on screen space, name
//            public int stop_pixel_x; // pixel coordinates of the latitude coordinates X in window space
//            public int stop_pixel_y; // pixel coordinates of the latitude coordinates X in window space
//            public List<Trip> adjListIncoming; //is the adjacency list of this node each node has a list of adjacent nodes
//            public List<Trip> adjListOutGoing; // and declare the same for outgoing nodes
    }

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

        public List<Stop> dataImportStops(File stopfile) throws IOException {
//            System.out.println("Started dataImport Stops Method");
            BufferedReader br = new BufferedReader(new FileReader(stopfile));
            String line = null;
            double stop_lat;
            double stop_lon;
            String[] stringLine;
            Stop stop = new Stop();
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
//                    System.out.println("First stops row skipped");
                    continue;
                }
                else{
                    stringLine = line.split("\t");
                    stop.stop_id = stringLine[0];
                    stop.stop_name = stringLine[1];
                    stop_lat = Double.parseDouble(stringLine[2]);
                    stop_lon = Double.parseDouble(stringLine[3]);
                    stop.stop_xy = Location.newFromLatLon(stop_lat, stop_lon);
                    double xdist = Math.abs(stop.stop_xy.x - Location.mapCentre_xy.x);
                    if ( xdist > max_xdist) {
                        max_xdist = xdist;
                    }
                    double ydist = Math.abs(stop.stop_xy.y - Location.mapCentre_xy.y);
                    if ( ydist > max_ydist) {
                        max_ydist = ydist;
                    }
                    stopsList.add(stop);
//                    System.out.println("Stop " + stop.stop_name + stop.stop_xy);
//                    System.out.println("Stop " + stop.stop_name + stop.stop_id + stop.stopxy);
                    i++;
                }

            }
            System.out.println("Max x kms: " + max_xdist + " Max y kms: " + max_ydist);
            double scale;
            double screenpixel_u = getDrawingAreaDimension().getWidth();
            double screenpixel_v= getDrawingAreaDimension().getHeight();
            double scale_x = screenpixel_u/max_xdist;
            double scale_y = screenpixel_v/max_ydist;
            scale = Math.min(scale_x, scale_y);
            System.out.println("scale " + scale + " scale_x " + scale_x + " Scale y " + scale_y);
            return stopsList;
            }

    public List<Trip> dataImportTrips(File tripfile) throws IOException {
        System.out.println("Started dataImport Trips Method");
        BufferedReader br = new BufferedReader(new FileReader(tripfile));
        String line = null;
        String[] stringLine;
        Trip trip = new Trip();
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
                continue;
            }
            else{
//                System.out.println("Line " + i);
                stringLine = line.split("\t");
                trip.trip_id = stringLine[0];
                int numStops = stringLine.length - 1;
//                System.out.println("Trip " + trip.trip_id + " First Stop " + stringLine[1]  + " Last Stop " + stringLine[numStops] + " No. stops " + numStops);
                trip.stopSequence.clear();
                for (int j = 1; j < stringLine.length; j++){
                    if (i == 1) {
//                        System.out.println("stop " + j + ": " + stringLine[j]);
                    }
                    trip.stopSequence.add(stringLine[j]);
                }
                tripsList.add(trip);
//                System.out.println("Trip " + trip.trip_id + trip.stopSequence);
                i++;
            }
        }
        return tripsList;
    }

    @Override
        protected void redraw(Graphics g) {
            // TO DO: rewrite this class. Public transport class extends this GUI class.
        for (StopMarker sm : stopMarkers) {
            sm.draw(g);
            }
        }

        @Override
        protected void onClick(MouseEvent e) {
            /*
             * This is the function I need to rewrite with the stops and trips OnClick functionality. If a node is clicked, display the information about it
             * If a trip is clicked display the information about that trip.
             */
//        int i = nodesMap.size();
//        for (Node n : nodesMap) {
//            if (squares.get(i).contains(e.getX(), e.getY())) {
//                squares.remove(i);
//                break;
//            }
            System.out.println("A mouse click event occurred."); //yet to define which node
//            getTextOutputArea().append("\nsquares remaining: ");
        }


        @Override
        protected void onSearch() { getTextOutputArea().setText(getSearchBox().getText()); }

        @Override
        protected void onMove(Move m) { makeMap(); }

        @Override
        protected void onLoad(File stopFile, File tripFile) throws IOException {

            //create some error messages - not really needed as this is already done for us I see
            String text1 = "stopFile not found";
            String text3 = " and tripFile not found";
            String text2 = " stopFile is not readable";
            String text4 = " tripFile is not readable";
            System.out.println("Started onLoad");

            if (stopFile.exists()) {
                text1 = stopFile.getName() + " is loaded";
                if (stopFile.canRead()) {
                    text2 = " and readable";
                }
                if (tripFile.exists()) {
                    text3 = " and " + tripFile.getName() + " is loaded";
                    if (tripFile.canRead()) {
                        text4 = " and readable";
                    }
                }
                getTextOutputArea().setText(text1 + text2 + text3 + text4);
            }

            List<Stop> stopData = dataImportStops(stopFile);
            System.out.println("Finished dataImport stopFile");
            List<Trip> tripData = dataImportTrips(tripFile);
            makeMap();
        }

public static void main(String[] args) {
            new DrawStops();
        }
        /**
         * stores the data for the stopmarkers
         * Draw each stop as a square of (try size 10 pixels to start with)
         * Centred on stop_xy
         * From the list of Stops
         */

        private static class StopMarker {
            public final Location xy;
            public final Color color = Color.cyan;
            public final int size = SQUARE_SIZE;

            public StopMarker(Location xy) {
                this.xy = xy;
            }

            public void draw(Graphics g) {
                g.setColor(color);
                Point point = this.xy.asPoint(xy, 1);
                g.fillRect(point.x, point.y, SQUARE_SIZE, SQUARE_SIZE);
            }
//        public boolean contains(int x, int y) {
//            return x > this.x && y > this.y && x < this.x + SQUARE_SIZE
//                && y < this.y + SQUARE_SIZE;
//        }

        }

    }

// code for COMP261 assignments
