import java.awt.*;

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
}
