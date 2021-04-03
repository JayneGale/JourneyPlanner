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
    //work out which is harder to fit, the map width or height - ie the larger scale
    // screen width / map width gives pixels / km
    public double ZOOM_FACTOR = 1.4;
    public double dx = 0;
    public double dy = 0;
//    double scale1 = mapDim.width / (38.92); // width of the screen vs width of the PT map in kms pixels per km
//    double scale2 = mapDim.height/ 31.82; // height of the screen vs height of the PT map pixels per km
    public double scale = 10; // default to smaller scale as that will be the best fit, can change later if need
    //Find the origin. Given the scale pixels/ km and the map centre xy in km, centre the map in the screen
    //the centre will be half the screen width and half the screen height
    //Map origin located ag minimum x location / scale and maximum x location / scale
    public Location origin = new Location ( (-20), 20);
    public int maxResultsList = 4;
    public String searchPrefix;
    public TrieNodeActions t_stops = new TrieNodeActions(); // the Trie for stop names
    public TrieNodeActions t_trips = new TrieNodeActions(); // the Trie for trip names

//    public Location origin = new Location ( (-19.457), 15.909);
//    public Location origin = new Location (-40,16);

    public JourneyPlanner() {
        // do something
    }
    @Override
    protected void onClick(MouseEvent e) {
        /*
         * If a node is clicked, display the name of that Stop
         * If a trip is clicked display the originating stop and final stop of that trip.
         */
        for (Stop s : stopsList) {
//            for (Trip t : s.tripsthruStop){ // I have yet to write the tripsthruStop structures
//            = all the trips that go through stop s, from origin to final destination
//            based on trips that include either an  incoming edge to. or outgoing edge from, that stop
//                t.isHighlightedTrip = false; // initialise back to false
            if(s.containsPoint(e.getX(), e.getY(), origin, scale)){
                s.isHighlighted = true;
                getTextOutputArea().setText("\nBus Stop " + s.stop_name);
//                getTextOutputArea().append("\nBus Stop " + s.stop_name);
//                for (t : s.tripsthruStop){
//                    t.isHighlightedTrip = true;
//                }
            }
            else s.isHighlighted = false;
        }
        for (Edge ed : edgeList) {
            if(ed.containsPoint(e.getX(), e.getY(), origin, scale)){
                ed.isHighlighted = true;
                getTextOutputArea().setText("\nFrom " + ed.fromStop.stop_name + " to " + ed.toStop.stop_name);
                // I haven't created this structure yet
                //                for (t : ed.tripsthruEdge){
                //                    t.isHighlightedTrip = true;
                //                }
            }
            else ed.isHighlighted = false;
        }

//        System.out.println("A mouse click event occurred." + e.getX() + e.getY()); //yet to define which node
    }

    @Override
    protected void onSearch() {
        //clear the test box of messages
        getTextOutputArea().setText(""); // clear the text box
        searchPrefix = getSearchBox().getText();
        System.out.println("searchPrefix " + searchPrefix);
//        TrieNodeActions p = new TrieNodeActions();
        List<String> results = new ArrayList<String>();
        results = t_stops.getAllFromPrefix(searchPrefix);
        int num = results.size();
        // return the first 6 results
        if (num > 0) {
            if (num > maxResultsList) {
                num = maxResultsList;
                getTextOutputArea().setText("Showing " + maxResultsList + " of " + results.size() + " Bus Stops");
            }
            for (int i = 0; i < num; i++) {
                getTextOutputArea().append("\n " + results.get(i));
            }
        }
        else {
            getTextOutputArea().setText("\n No Bus Stops match your search");
        }
    }

    @Override
    protected void redraw(Graphics g) {
//        // TO DO: rewrite this class. Public transport class extends this GUI class.
        graph.draw(g, origin, scale);
    }

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
//        Zoom
//        Calculate width and height in km
        // x y dimensions change not Pixel dimensions
        // Scale origin and Zoom are not working properly so most of the following code is to help me understand the different dimension systems
        int widthScr = getDrawingAreaDimension().width;
        int heightScr = getDrawingAreaDimension().height;
        Point botLeft = new Point(0, 0);
        Point topLeft = new Point(0, heightScr);
        Point botRight = new Point(widthScr, 0);
        Point topRight = new Point(widthScr, heightScr);
// check some parameters
//        Point middleScr = new Point ((int)((topRight.x - topLeft.x)/2.0f), (int)((topLeft.y - botLeft.y)/2.0f));
//        Point mapCentreScr = Location.mapCentre_xy.asPoint(origin, scale);
//        System.out.println("middleScr " + middleScr + "  and map centre " + mapCentreScr);
        System.out.println("Drawing dimensions: widthScr " + widthScr + "  and heightScr " + heightScr + " topLeft " + topLeft + " + topRight " + topRight + " botLeft " + botLeft + " botRight " + botRight);
// Work out dimensions in kms
        Location topLeftKms = Location.newFromPoint(topLeft, origin, scale);
        Location topRightKms = Location.newFromPoint(topRight, origin, scale);
        Location botLeftKms = Location.newFromPoint(botLeft, origin, scale);
        Location botRightKms = Location.newFromPoint(botRight, origin, scale);
//        Location middleKms = new Location ((topRightKms.x - topLeftKms.x)/2.0f, (botLeftKms.y - topLeftKms.y)/2.0f); // may be reversed for u to y
//        System.out.println("Km dimensions: middleKms" + middleKms + " topLeft x " + topLeftKms.x + " y " + topLeftKms.y + " topRight " + topRightKms + " botLeft " + botLeftKms + " botRight " + botRightKms);
        double widthKms = topRightKms.x - topLeftKms.x;
        double heightKms = botLeftKms.y - topLeftKms.y;
        double newwidthKms;
        double newheightKms;
//        System.out.println("Km dimensions: width " + widthKms + " height " + heightKms);

        switch(m) {
            case EAST:
                dx = delta_kms;
                dy = 0;
//            System.out.println("Direction: EAST" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
                break;
            case WEST:
                dx = -delta_kms;
                dy = 0;
                System.out.println("Direction: WEST" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
                break;
            case NORTH:
                dx = 0;
                dy = delta_kms;
//                System.out.println("Direction: NORTH" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
                break;
            case SOUTH:
                dx = 0;
                dy = -delta_kms;
//                System.out.println("Direction: SOUTH" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);
                break;
            case ZOOM_IN:
                //– Zoom-in: increase scale
                scale = scale * ZOOM_FACTOR;
                //Zoom in change the origin - see fewer kms
                newwidthKms = widthKms/ZOOM_FACTOR;
                newheightKms = heightKms/ZOOM_FACTOR;
                dx = (widthKms - newwidthKms)/2.0f;
                dy = (newheightKms - heightKms)/2.0f;
                System.out.println("Direction: ZOOM IN: widthKms: " + widthKms + " newwidthKms: " + newwidthKms + " dx " + dx + " dy " + dy + " old origin: " + origin + " scale " + scale);
                break;
            case ZOOM_OUT:
                //– Zoom-out: increase scale - you see more kms
                scale = scale / ZOOM_FACTOR;
                //Zoom out change the origin - move it outwards ie negative
                newwidthKms = widthKms*ZOOM_FACTOR;
                newheightKms = heightKms*ZOOM_FACTOR;
                dx = (widthKms - newwidthKms)/2.0f;
                dy = (newheightKms - heightKms)/2.0f;
                // these should both be negative for zoom out
                System.out.println("Direction: ZOOM OUT: widthKms: " + widthKms + " newwidthKms: " + newwidthKms + " dx " + dx + " dy " + dy + " old origin: " + origin + " scale " + scale);
                break;
            default:
                dx = 0;
                dy = 0;
        }
        origin  = origin.moveBy(dx, dy);
        System.out.println("Line 173 JP New origin " + origin + " scale " + scale);
    }
    @Override
    protected void onLoad(File stopFile, File tripFile) throws IOException {
        //create some error messages - not really needed as this is already done for us I see
//        CheckDataFiles(stopFile, tripFile);
        // read data files
        ReadDataFiles r = new ReadDataFiles();
        stopsList = r.ReadStops(stopFile);
        tripsList = r.ReadTrips(tripFile, stopsList);
//        int numTrips = tripsList.size();  // there are 101 trips

// Check it all works
//        Trip lastTrip = tripsList.get(numTrips-1);
//        int numLStops = lastTrip.tripSequence.size();
//        Stop lastTripLastStop = lastTrip.tripSequence.get(numLStops - 1);

        //Create the edgeList and the Graph of stops and edges between stops
        MakeMap map = new MakeMap();
        edgeList = map.CreateEdgeList(tripsList, stopsList);

//        stopsList = m.CreateAdjacencyLists(edgeList, stopsList);
        // test an Edge that should exist exists
        //        Stop Mandorah = new Stop("OOBUS826", "Mandorah 826", -12.443,130.768, Location.newFromLatLon(-12.443, 130.768), null, null, null);
        //        Stop Cullen = new Stop("OOBUS825", "Cullen Bay 825", -12.4517,130.82, Location.newFromLatLon(-12.4517,130.82),null, null, null);
        //        Edge MandorahCullen = new Edge (Mandorah, Cullen);
        //        boolean listworks = edgeList.contains(MandorahCullen);
        //        System.out.println("JP ln 95 Finished MakeMap edgeFile " + listworks);
        //        ...and damn the boolean shows that edgeList doesn't_stops contain Mandorah to Cullen Bay even though the Edge is in there

//        Set up the TrieNode structure and put all the stop names into it
        // Add all the stop names to Trie structures by iterating through the TrieNodeActions on the Stopnames and Tripnames
//        TrieNodeActions t_stops = new TrieNodeActions();
        for (Stop s : stopsList){
            String n = s.stop_name;
            t_stops.addName(n);
        }
//        System.out.println("Chars in Trie  " + charsInTrie.size()); toLowerCase reduces the search from 62 to 41

        for (Trip t : tripsList){
            String n = t.name;
            t_trips.addName(n);
            // Since I generate the trip names from the stop names, no chars are ever added to the Trie span
        }
//        String testName = "Casuarina Interchange";
//        String returnedName = t_stops.searchName(testName);
//        System.out.println("JP ln 250 returnedName for " + testName + " is " + returnedName);
//        List<String> results = new ArrayList<String>();
//        results = t_stops.getAllFromPrefix("C");
//        System.out.println("JP ln 231 " + results.size());
//        System.out.println("JP ln 232 results 0  " + results.get(0));
//        System.out.println("JP ln 232 results last " + results.get(results.size() - 1));

//        Now all the stop and trip names are in a Trie structure
//        And I can search for a Boolean is it there or not and retrieve a name from the Trie

        graph = map.CreateGraph(stopsList, edgeList);

//        Stop firstStop = stopsList.get(0);
//        String name = firstStop.stop_name;
//        String neighbour = stopsList.get(0).adjListOutgoing.get(0).toStop.stop_name;
//        System.out.println("Finished MakeMap CreateGraph " + name + neighbour);
//         trieStopNames = new TrieNode;
    }
//    private void CheckDataFiles(File stopFile, File tripFile) {
//        String text1 = "stopFile not found";
//        String text3 = " and tripFile not found";
//        String text2 = " stopFile is not readable";
//        String text4 = " tripFile is not readable";
//        if (stopFile.exists()) {
//            text1 = stopFile.getName() + " is loaded";
//            if (stopFile.canRead()) {
//                text2 = " and readable";
//            }
//            if (tripFile.exists()) {
//                text3 = " and " + tripFile.getName() + " is loaded";
//                if (tripFile.canRead()) {
//                    text4 = " and readable";
//                }
//            }
//            getTextOutputArea().setText(text1 + text2 + text3 + text4);
//        }
//    }
    public static void main(String[] args) {
        new JourneyPlanner();
    }
}

