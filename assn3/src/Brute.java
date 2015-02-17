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
public class Brute {

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
        for (int i = 0; i < points.length; i++) {
            points[i].draw();
        }
    }

    private void drawLineFrom(Point point1, Point point2) {
        point1.drawTo(point2);
    }

    private void printPointsInLine(Point point1, Point point2, Point point3, Point point4) {
        // TODO: Order sequence of points in line. Currently unordered.
        System.out.printf("%s -> %s -> %s -> %s\n", point1.toString(), point2.toString(),
                point3.toString(), point4.toString());
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Brute brute = new Brute();
        Point[] points = brute.readPointsFromFile(fileName);
        brute.drawAllPoints(points);
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Point point1 = points[i];
                        Point point2 = points[j];
                        Point point3 = points[k];
                        Point point4 = points[l];
                        double slope12 = point1.slopeTo(point2);
                        double slope13 = point1.slopeTo(point3);
                        double slope14 = point1.slopeTo(point4);
                        if ((slope12 == slope13) && (slope13 == slope14)) {
                            brute.printPointsInLine(point1, point2, point3, point4);
                            //brute.drawLineFrom(point1, point2);
                            //brute.drawLineFrom(point1, point3);
                            //brute.drawLineFrom(point1, point4);
                            //brute.drawLineFrom(point1, point2);
                        }
                    }
                }
            }
        }
    }
}
