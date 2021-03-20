import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;
import java.lang.*;

public class JourneyPlanner extends GUI {
    public List<Stop> stopsList = new ArrayList<>();
    public List<Trip> tripsList = new ArrayList<>();
    public List<Edge> edgeList = new ArrayList<>(); // directed edges
    public Graph graph = new Graph(stopsList, edgeList);
    public Dimension mapDim = getDrawingAreaDimension();
    //work out which is harder to fit, the map width or height
    // screen width / map width gives pixels / km
    public double ZOOM_FACTOR = 1.4;
    public double dx = 0;
    public double dy = 0;
    double scale1 = mapDim.width / 38.92; // width of the screen vs width of the PT map in kms pixels per km
    double scale2 = mapDim.height/ 31.82; // height of the screen vs height of the PT map pixels per km
    public double scale = scale1; // default to width scale as likely best fit, can change to scale2 later if need
    //Find the origin. Given the scale pixels/ km and the map centre xy in km, centre the map in the screen
    //Map origin located ag minimum x location / scale and maximum x location / scale
    public Location origin = new Location ( (-19.457), 15.909);
//    public Location origin = new Location (-40,16);
    public JourneyPlanner() {
        // middle of drawing area// do something
    }
    /** Backlog
     * TO DO: Zoom map
     * TO DO: Display attributes of Stops on Map
     *
     * Write report Wed 17 March
     * Submit midnight Wed 17 March
     * Nice to have: Completion
     * Create Trie Structure for Stop names
     * Implement Trie Structure#
     * Nice to have: Display attributes of Edges on Map
     * Nice to have: Refactor DrawStops methods into separate Classes; call the Classes from the JourneyPlannerMain Class Tue 16 March
     *
     *
     * DONE
     * Load files into Classes
     * Implement Stop class except for draw method, Adjacency Lists
     * Implement Trip Class except for fromStop and ToStop
     * Take out the StopMarkers ArrayList and just use Stops
     * Create separate methods for Stops, Trips and Data Input
     * Create Graph structure (to make Graphics g work)
     * Draw the Map of the Stops
     * Create the (directional) edges of the Graph from the Stop sequence
     * Create the in and out adjacency Lists for each Stop
     * Create fromStop and toStop in the Trips structure
     * Centre map
     * Pan map
     * Start making addTrie function
     */

    @Override
    protected void redraw(Graphics g) {
//        // TO DO: rewrite this class. Public transport class extends this GUI class.
        graph.draw(g, origin, scale);
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
    protected void onMove(Move m) {
        // from the lecture notes
        //        for Pan
        // on any of the move arrows, we set a distance (eg dx = 10km) for each click on the arrow
        // for move, we move the origin
        //         dx = loc.x-origin.x;
        //         dy = origin.y– loc.y;
        //         orig.x = orig.x + dx;
        //         orig.y = orig.y + dy;

        double delta_kms = 10;
        if(m == Move.EAST)        {
            dx = delta_kms;
            dy = 0;
//            System.out.println("Direction: EAST" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
        };
        if(m == Move.WEST)        {
            dx = -delta_kms;
            dy = 0;
            System.out.println("Direction: WEST" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
        }
        if(m == Move.NORTH)        {
            dx = 0;
            dy = delta_kms;
            System.out.println("Direction: NORTH" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
        }
        if(m == Move.SOUTH)        {
            dx = 0;
            dy = -delta_kms;
            System.out.println("Direction: SOUTH" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
        };
//        Zoom
//        Calculate width and height in km
        // x y dimensions change not Pixel dimensions
        int widthScr = getDrawingAreaDimension().width;
        int heightScr = getDrawingAreaDimension().height;
        Point botLeft = new Point(0, 0);
        Point topLeft = new Point(0, heightScr);
        Point botRight = new Point(widthScr, 0);
        Point topRight = new Point(widthScr, heightScr);
// check some parameters
        Point middleScr = new Point ((int)((topRight.x - topLeft.x)/2.0f), (int)((topLeft.y - botLeft.y)/2.0f));
        Point mapCentreScr = Location.mapCentre_xy.asPoint(origin, scale);
        System.out.println("middleScr " + middleScr + "  and map centre " + mapCentreScr);
        System.out.println("Drawing dimensions: widthScr " + widthScr + "  and heightScr " + heightScr + " topLeft " + topLeft + " + topRight " + topRight + " botLeft " + botLeft + " botRight " + botRight);
// Now work out dimensions in kms
        Location topLeftKms = Location.newFromPoint(topLeft, origin, scale);
        Location topRightKms = Location.newFromPoint(topRight, origin, scale);
        Location botLeftKms = Location.newFromPoint(botLeft, origin, scale);
//        Location botRightKms = Location.newFromPoint(botRight, origin, scale);
//        Location middleKms = new Location ((topRightKms.x - topLeftKms.x)/2.0f, (botLeftKms.y - topLeftKms.y)/2.0f); // may be reversed for u to y
//        System.out.println("Km dimensions: middleKms" + middleKms + " topLeft x " + topLeftKms.x + " y " + topLeftKms.y + " topRight " + topRightKms + " botLeft " + botLeftKms + " botRight " + botRightKms);
        double widthKms = topRightKms.x - topLeftKms.x;
        double heightKms = botLeftKms.y - topLeftKms.y;
        System.out.println("Km dimensions: width " + widthKms + " height " + heightKms);

        if(m == Move.ZOOM_IN)        {
            //– Zoom-in: increase scale
            scale = scale * ZOOM_FACTOR;
            //Zoom in change the origin - see fewer kms
            double newwidthKms = widthKms/ZOOM_FACTOR;
            double newheightKms = heightKms/ZOOM_FACTOR;
            dx = (widthKms - newwidthKms)/2.0f;
            dy = (heightKms - newheightKms)/2.0f;
            System.out.println("Direction: ZOOM IN: widthKms: " + widthKms + " newwidthKms: " + newwidthKms + " dx " + dx + " dy " + dy + " old origin: " + origin + " scale " + scale);
            widthKms = newwidthKms;
            heightKms = newheightKms;
        };
        if(m == Move.ZOOM_OUT)        {
            //– Zoom-out: increase scale - you see more kms
            scale = scale / ZOOM_FACTOR;
            //Zoom out change the origin - move it outwards ie negative
            double newwidthKms = widthKms*ZOOM_FACTOR;
            double newheightKms = heightKms*ZOOM_FACTOR;
            dx = (widthKms - newwidthKms)/2.0f;
            dy = (heightKms - newheightKms)/2.0f;
            // these should both be negative for zoom out
            System.out.println("Direction: ZOOM OUT: widthKms: " + widthKms + " newwidthKms: " + newwidthKms + " dx " + dx + " dy " + dy + " old origin: " + origin + " scale " + scale);
            widthKms = newwidthKms;
            heightKms = newheightKms; //hmm why is this not used again?

        };
        origin  = origin.moveBy(dx, dy);
        System.out.println("Line 173 JP New origin " + origin + " scale " + scale);

    }
    @Override
    protected void onLoad(File stopFile, File tripFile) throws IOException {
        //create some error messages - not really needed as this is already done for us I see
//        System.out.println("Started onLoad");
        CheckDataFiles(stopFile, tripFile);
        // read data files
        ReadDataFiles r = new ReadDataFiles();
        stopsList = r.ReadStops(stopFile);
//        System.out.println("Finished dataImport stopFile");
        tripsList = r.ReadTrips(tripFile, stopsList);
        int numTrips = tripsList.size();  // there are 101 trips
//        Trip lastTrip = tripsList.get(numTrips-1);
//        int numLStops = lastTrip.tripSequence.size();
//        Stop lastTripLastStop = lastTrip.tripSequence.get(numLStops - 1);
//        System.out.println("JP onLoad l 88 tripsList: Last trip " + lastTrip.trip_id + " has " + numLStops + " stops " + " the last stop is " +  lastTripLastStop.stop_id + " " + lastTripLastStop.stop_name);
//        System.out.println("Finished dataImport tripFile and first element" + tripsList.get(0).tripSequence.get(0).stop_name + tripsList.get(numTrips -1).tripSequence.get(0) );

        //Create the edgeList and the Graph of stops and edges between stops
        MakeMap map = new MakeMap();
        edgeList = map.CreateEdgeList(tripsList, stopsList);
//        System.out.println(stopsList.get(0).stop_name);

//        stopsList = m.CreateAdjacencyLists(edgeList, stopsList);
        // test an Edge that should exist exists
        //        Stop Mandorah = new Stop("OOBUS826", "Mandorah 826", -12.443,130.768, Location.newFromLatLon(-12.443, 130.768), null, null, null);
        //        Stop Cullen = new Stop("OOBUS825", "Cullen Bay 825", -12.4517,130.82, Location.newFromLatLon(-12.4517,130.82),null, null, null);
        //        Edge MandorahCullen = new Edge (Mandorah, Cullen);
        //        boolean listworks = edgeList.contains(MandorahCullen);
        //        System.out.println("JP ln 95 Finished MakeMap edgeFile " + listworks);
        //        ...and damn the boolean shows that edgeList doesn't_stops contain Mandorah to Cullen Bay even though the Edge is in there
//        Now set up the TrieNode structure and put all the stop names into it

        // Add all the stop names to Trie structures by iterating through the TrieNodeActions on the Stopnames and Tripnames

        TrieNodeActions t_stops = new TrieNodeActions();
        for (Stop s : stopsList){
            String n = s.stop_name;
            t_stops.addName(n);
            }

        TrieNodeActions t_trips = new TrieNodeActions();
        for (Trip t : tripsList){
            String n = t.name;
            t_trips.addName(n);
        }

        //Now all the stop and trip names are in a Trie structure
        // And I can search for a Boolean is it there or not - so now have to work out how to return a name and allNames
//        int num = tripsList.size();
//        String testname = tripsList.get(0).name;
//        System.out.println("JP ln 219 " + num);
//        System.out.println("JP ln 221 first trip name : "  + testname);
//        boolean isTripnameInTrie = t_trips.search(testname);
//        System.out.println("JP ln 221 see if Trip name " + testname + " is in the Trie : "  + isTripnameInTrie);

        graph = map.CreateGraph(stopsList, edgeList);

//        Stop firstStop = stopsList.get(0);
//        String name = firstStop.stop_name;
//        String neighbour = stopsList.get(0).adjListOutgoing.get(0).toStop.stop_name;
//        System.out.println("Finished MakeMap CreateGraph " + name + neighbour);
//         trieStopNames = new TrieNode;
    }
    private void CheckDataFiles(File stopFile, File tripFile) {
        String text1 = "stopFile not found";
        String text3 = " and tripFile not found";
        String text2 = " stopFile is not readable";
        String text4 = " tripFile is not readable";
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
    }
    public static void main(String[] args) {
        new JourneyPlanner();
    }
}

