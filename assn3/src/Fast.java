import java.util.Arrays;

/**
 * Created by bfelder on 2/16/15.
 */

/*
File in format:
% more input6.txt
6
19000  10000
18000  10000
32000  10000
21000  10000
 1234   5678
14000  10000
 */
public class Fast {

    private Point[] readPointsFromFile(String fileName) {
        assert fileName != null;
        Point[] points = null;
        In input = new In(fileName);
        if (input.hasNextLine()) {
            String lineCountString = input.readLine();
            int lineCount = Integer.parseInt(lineCountString);
            points = new Point[lineCount];
            for (int i = 0; i < lineCount; i++) {
                int x = input.readInt();
                int y = input.readInt();
                Point thePoint = new Point(x, y);
                points[i] = thePoint;
            }
        }
        if (points == null)
            throw new RuntimeException("Could not get points from file.");
        return points;
    }

    private void drawAllPoints(Point[] points) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i = 0; i < points.length; i++) {
            points[i].draw();
        }
    }

    private void drawLineFrom(Point point1, Point point2) {
        point1.drawTo(point2);
    }

    private void printPointsInLine(Point [] points) {
        if (points == null || points.length == 0)
            return;
        System.out.print(points[0].toString());
        for (int i = 1; i < points.length; i++) {
            System.out.print(" -> " + points[i].toString());
        }
        System.out.println();
    }

    // Returns the first set of collinear points with the reference.
    // Do we need to find ALL collinear points with the reference?
    private Point[] findCollinear(Point reference, Point[] toTest) {
        int startIndex = -1, endIndex = -1;
        double previousSlope = Double.MAX_VALUE;
        for (int i = 0; i < toTest.length; i++) {
            double currentSlope = reference.slopeTo(toTest[i]);
            if (currentSlope == previousSlope) {
                endIndex = i;
                if (startIndex == -1)
                    startIndex = i - 1;
            } else if (startIndex != -1) {
                // Slope has changed, and we already have one match
                break;
            }
            previousSlope = currentSlope;
        }
        if (startIndex == -1)
            return null;
        Point[] toReturn = new Point[(endIndex - startIndex) + 1];
        for (int i = startIndex; i <= endIndex; i++) {
            toReturn[i - startIndex] = toTest[i];
        }
        return toReturn;
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Fast fast = new Fast();
        Point[] points = fast.readPointsFromFile(fileName);
        Merge.sort(points);
        fast.drawAllPoints(points);
        Point[] collinearPoints = new Point[4];
        Point currentPoint = null;
        for (int i = 0; i < points.length; i++) {
            Point[] pointsCopy = Arrays.copyOf(points, points.length);
            currentPoint = pointsCopy[i];
            Arrays.sort(pointsCopy, currentPoint.SLOPE_ORDER);
            collinearPoints = fast.findCollinear(currentPoint, pointsCopy);
            if (collinearPoints != null)
                fast.printPointsInLine(collinearPoints);
        }
    }
}
