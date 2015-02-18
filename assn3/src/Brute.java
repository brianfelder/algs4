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

    private void printPointsInLine(Point [] fourPoints) {
        // TODO: Order sequence of points in line. Currently unordered.
        System.out.printf("%s -> %s -> %s -> %s\n", fourPoints[0].toString(), fourPoints[1].toString(),
                fourPoints[2].toString(), fourPoints[3].toString());
    }

    public static void main(String[] args) {
        String fileName = args[0];
        Brute brute = new Brute();
        Point[] points = brute.readPointsFromFile(fileName);
        Merge.sort(points);
        brute.drawAllPoints(points);
        Point[] currentPoints = new Point[4];
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        currentPoints[0] = points[i];
                        currentPoints[1] = points[j];
                        currentPoints[2] = points[k];
                        currentPoints[3] = points[l];
                        double slope12 = currentPoints[0].slopeTo(currentPoints[1]);
                        double slope13 = currentPoints[0].slopeTo(currentPoints[2]);
                        double slope14 = currentPoints[0].slopeTo(currentPoints[3]);
                        if ((slope12 == slope13) && (slope13 == slope14)) {
                            brute.printPointsInLine(currentPoints);
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
