import java.awt.*;
import java.util.List;

public class Stop {
    public boolean isHighlighted = false;
    String stop_id;
    String stop_name;
    double stop_lat;
    double stop_lon;
    Location stop_xy;
    int boxSize = 6;
    int halfboxSize = boxSize/2;
    // TO DO: create adjacency lists from Trip sequences
    public List<Edge> adjListIncoming;
    public List<Edge> adjListOutgoing;
    public List<Trip> tripsthruStop;

    public Stop(String stop_id, String name, double stop_lat, double stop_lon, Location stop_xy, List<Edge> adjListIncoming, List<Edge> adjListOutgoing, List<Trip> tripsthruStop) {
        this.stop_id = stop_id;
        this.stop_name = name;
        this.stop_lat = stop_lat;
        this.stop_lon = stop_lon;
        this.stop_xy = stop_xy;
        this.adjListIncoming = adjListIncoming;
        this.adjListOutgoing = adjListOutgoing;
        this.tripsthruStop = tripsthruStop;
    }

    public boolean containsPoint(int screenX, int screenY, Location origin, double scale) {
        Point xyPoint = stop_xy.asPoint(origin, scale);

        if (screenX < xyPoint.x - halfboxSize || screenX > xyPoint.x + halfboxSize) {
            return false;
        }
        //truth is whether its now in y limits
        return (screenY >= xyPoint.y - halfboxSize && screenY <= xyPoint.y + halfboxSize);
    }

    public void draw(Graphics g, Location origin, double scale) {
        Point xyPoint = stop_xy.asPoint(origin, scale);
        g.fillRect(xyPoint.x-halfboxSize, xyPoint.y-halfboxSize, boxSize, boxSize);
        // Todo make box size a property of a stop and a public constant at the top of the Stop
    }
}


