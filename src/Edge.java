import java.awt.*;
import java.math.*;

public class Edge {
    //    public int edge_id; // do I need this?
    public Stop fromStop;
    public Stop toStop;
    public boolean isHighlighted = false;

    public Edge(Stop fromStop, Stop toStop) {
        this.fromStop = fromStop;
        this.toStop = toStop;

        //needs a draw method
    }

    public void draw(Graphics g, Location origin, double scale) {
        Point fromPoint = fromStop.stop_xy.asPoint(origin, scale);
        Point toPoint = toStop.stop_xy.asPoint(origin, scale);
        g.drawLine(fromPoint.x, fromPoint.y, toPoint.x, toPoint.y);
    }
    public boolean containsPoint(int screenX, int screenY, Location origin, double scale) {
        Point fromStop_xy = fromStop.stop_xy.asPoint(origin, scale);
        Point toStop_xy = toStop.stop_xy.asPoint(origin, scale);

        double lx1 = fromStop_xy.x;
        double ly1 = fromStop_xy.y;
        double lx2 = toStop_xy.x;
        double ly2 = toStop_xy.y;
//  I ran out of time to calculate the distance from the mouse click to the edge line
//        if (dist_sq > tol) {
            return false;
//        }
        //truth is whether its now in y limits
//        return (screenY >= xyPoint.y - halfboxSize && screenY <= xyPoint.y + halfboxSize);
    }

}
