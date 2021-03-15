import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
import java.awt.Point;

public class JourneyPlanner extends GUI {
    public List<Stop> stopsList = new ArrayList<>();
    public List<Trip> tripsList = new ArrayList<>();
    public List<Edge> edgeList = new ArrayList<>(); // directed edges
    public Graph graph = new Graph(stopsList, edgeList);
    public Location origin = new Location (-40,16);
    public double scale = 20;
    public double dx = 0;
    public double dy = 0;
    // if there exists a stop sequence AB in any Tripsequence then the edge from A to B exists
    // if there exists a stop sequence BA in any Tripsequence, the edge from B to A exists

    public JourneyPlanner() {
        // do something
    }
    /** Backlog
     * TO DO: Pan map
     * TO DO: Zoom map
     * TO DO: Display attributes of Stops on Map
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
        // on any of the move arrows, we set a distance (eg dx = 10km) for each click on the arrow
        // for move, we move the origin
        //         dx = loc.x-origin.x;
        //         dy = origin.y– loc.y;
        //         orig.x = orig.x + dx;
        //         orig.y = orig.y + dy;

        double delta_kms = 10;
        if(m == Move.EAST)        {
            dx = -delta_kms;
            dy = 0;
            System.out.println("Direction: EAST" + " dx " + dx + " dy " + dy + " new origin: x " + origin.x + " y " +origin.y);

        };
        if(m == Move.WEST)        {
            dx = delta_kms;
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
//        origin = new Location (origin.x +	dx,origin.y + dy);
        origin  = origin.moveBy(dx, dy);
    }
    @Override
    protected void onLoad(File stopFile, File tripFile) throws IOException {
        //create some error messages - not really needed as this is already done for us I see
        CheckDataFiles(stopFile, tripFile);

        // read data files
        ReadDataFiles r = new ReadDataFiles();
        stopsList = r.ReadStops(stopFile);
//        System.out.println("Finished dataImport stopFile");
        tripsList = r.ReadTrips(tripFile, stopsList);
        int numTrips = tripsList.size();  // there are 101 trips
        Trip lastTrip = tripsList.get(numTrips-1);
        int numLStops = lastTrip.tripSequence.size();
        Stop lastTripLastStop = lastTrip.tripSequence.get(numLStops - 1);
//        System.out.println("JP onLoad l 88 tripsList: Last trip " + lastTrip.trip_id + " has " + numLStops + " stops " + " the last stop is " +  lastTripLastStop.stop_id + " " + lastTripLastStop.stop_name);
//        System.out.println("Finished dataImport tripFile and first element" + tripsList.get(0).tripSequence.get(0).stop_name + tripsList.get(numTrips -1).tripSequence.get(0) );

        //Create the edgeList and the Graph of stops and edges between stops
        MakeMap map = new MakeMap();
        edgeList = map.CreateEdgeList(tripsList, stopsList);
        System.out.println("JP ln 95 check adjLists exist look at the first stop name");
        System.out.println(stopsList.get(0).stop_name);

//        stopsList = m.CreateAdjacencyLists(edgeList, stopsList);
        // test an Edge that should exist exists
        //        Stop Mandorah = new Stop("OOBUS826", "Mandorah 826", -12.443,130.768, Location.newFromLatLon(-12.443, 130.768), null, null, null);
        //        Stop Cullen = new Stop("OOBUS825", "Cullen Bay 825", -12.4517,130.82, Location.newFromLatLon(-12.4517,130.82),null, null, null);
        //        Edge MandorahCullen = new Edge (Mandorah, Cullen);
        //        boolean listworks = edgeList.contains(MandorahCullen);
        //        System.out.println("JP ln 95 Finished MakeMap edgeFile " + listworks);
        //        ...and damn the boolean shows that edgeList doesn't contain Mandorah to Cullen Bay even though the Edge is in there

        graph = map.CreateGraph(stopsList, edgeList);

//        Stop firstStop = stopsList.get(0);
//        String name = firstStop.stop_name;
//        String neighbour = stopsList.get(0).adjListOutgoing.get(0).toStop.stop_name;
//        System.out.println("Finished MakeMap CreateGraph " + name + neighbour);
    }
    private void CheckDataFiles(File stopFile, File tripFile) {
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
    }

    public static void main(String[] args) {
        new JourneyPlanner();
    }
}

