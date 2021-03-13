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
    // if there exists a stop sequence AB in any Tripsequence then the edge from A to B exists
    // if there exists a stop sequence BA in any Tripsequence, the edge from B to A exists

    public JourneyPlanner() {
        // do something
    }
    /** Backlog
     * TO DO: Create Graph structure (to make Graphics g work)
     * TO DO: Draw the Map of the Stops
     * TO DO: create the (directional) edges of the Graph from the Stop sequence
     * TO DO: Create the in and out adjacency Lists for each Stop
     * To DO: Create fromStop and toStop in the Trips structure
     * TO DO: Centre map
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
     * DONE
     * Load files into Classes
     * Implement Stop class except for draw method, Adjacency Lists
     * Implement Trip Class except for fromStop and ToStop
     * Take out the StopMarkers ArrayList and just use Stops
     * Create separate methods for Stops, Trips and Data Input
     */

    @Override
    protected void redraw(Graphics g) {
//        // TO DO: rewrite this class. Public transport class extends this GUI class.
//        for (Stop s : stopList) {
//            s.draw(g);
//        }
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

    }
    @Override
    protected void onLoad(File stopFile, File tripFile) throws IOException {
        //create some error messages - not really needed as this is already done for us I see
        CheckDataFiles(stopFile, tripFile);
        ReadDataFiles r = new ReadDataFiles();
        stopsList = r.ReadStops(stopFile);
        System.out.println("Finished dataImport stopFile");
        tripsList = r.ReadTrips(tripFile, stopsList);
        System.out.println("Finished dataImport tripFile");
        MakeMap m = new MakeMap();
        edgeList = m.CreateEdgeList(tripsList);
        System.out.println("Finished MakeMap edgeFile");
        graph = m.CreateGraph(stopsList, edgeList);
        Stop firstStop = stopsList.get(0);
        String name = firstStop.stop_name;
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

