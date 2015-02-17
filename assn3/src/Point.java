import java.util.Comparator;

public class Point implements Comparable<Point> {
    public final Comparator<Point> SLOPE_ORDER;        // compare points by slope to this point

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.SLOPE_ORDER = new SlopeComparator();
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        assert that != null;
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that) {
        assert that != null;
        if (this.y > that.y)
            return 1;
        if (this.y < that.y)
            return -1;
        if (this.x > that.x)
            return 1;
        if (this.x < that.x)
            return -1;
        return 0;
    }

    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0)
            return Double.NEGATIVE_INFINITY;
        if ((that.x - this.x) == 0)
            return Double.POSITIVE_INFINITY;
        return ((that.y - this.y) / (that.x - this.x));
    }

    private class SlopeComparator implements Comparator<Point> {
        private Point myPoint;
        public SlopeComparator(Point thePoint) {
            assert thePoint != null;
            myPoint = thePoint;
        }
        public int compare(Point a, Point b) {
            if (myPoint.slopeTo(a) < myPoint.slopeTo(b))
                return -1;
            if (myPoint.slopeTo(a) > myPoint.slopeTo(b))
                return 1;
            return 0;
        }
    }
}