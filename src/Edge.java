public class Edge {
    //    public int edge_id; // do I need this?
    public Stop fromStop;
    public Stop toStop;

    public Edge(Stop fromStop, Stop toStop) {
        this.fromStop = fromStop;
        this.toStop = toStop;

        //needs a draw method
    }
}
