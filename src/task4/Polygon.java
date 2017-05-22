package task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Algorithm based on the first half of the Grahm Scan convex hull algorithm,
 * as detailed here (http://geomalgorithms.com/a10-_hull-1.html).
 * <p>
 * The first step of the Grahm Scan is to sort the points in a set based on the angle made with the X axis
 * when drawing a line through the point and the lower-right point of the set.
 * <p>
 * If two or more points form the same angle with the X axis (i.e. are aligned with respect to the reference point)
 * then those points should be sorted based on distance from the reference point.
 * <p>
 * https://cs.stackexchange.com/a/52627
 */
public class Polygon {
    private int n;        // array index counter
    private Point[] a;    // the points

    public Polygon(int pointsCnt) {
        this.n = 0;
        this.a = new Point[pointsCnt];
    }

    // is p0->a->b a counter-clockwise turn?
    // +1 if counter-clockwise, -1 if clockwise, 0 if collinear
    private int ccw(Point p0, Point a, Point b) {
        double area2 = (a.x - p0.x) * (b.y - p0.y) - (b.x - p0.x) * (a.y - p0.y);
        if (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else return 0;
    }

    private int distCompare(Point p0, Point a, Point b) {
        int distA = (p0.x - a.x) * (p0.x - a.x) + (p0.y - a.y) * (p0.y - a.y);
        int distB = (p0.x - b.x) * (p0.x - b.x) + (p0.y - b.y) * (p0.y - b.y);
        return distA - distB;
    }

    private int angleCompare(Point p0, Point a, Point b) {
        int left = ccw(p0, a, b);
        if (left == 0) return distCompare(p0, a, b);
        return left;
    }

    public void sort()
    {
        // sort array of points and determine point with min Y coordinate (and max X coordinate among some Y)
        Comparator<Point> sortByYminXmax = (o1, o2) -> {
            int res = 0;
            if (!Objects.isNull(o1) && !Objects.isNull(o2)) {
                res = Integer.compare(o1.y, o2.y);
                if (res == 0) res = Integer.compare(o2.x, o1.x);
            }
            return res;
        };

        Comparator<Point> compareByAngle = (o1, o2) -> {
            int res = 0;
            if (!Objects.isNull(o1) && !Objects.isNull(o2))
                res = Polygon.this.angleCompare(a[0], o1, o2);
            return res;
        };

        Arrays.sort(a, sortByYminXmax);
        Arrays.sort(a, compareByAngle);
    }

    // add point p to end of polygon
    public void add(Point p) {
        a[n++] = p; // add point
    }

    @Override
    public String toString() {
        return Arrays.stream(a).map(point -> {
            if (!Objects.isNull(point))
                return point.toString();
            else
                return "";
        }).collect(Collectors.joining(" "));
    }

    // input number of points
    private static int inputPointsNum(int maxN) {
        int pNum = 0;
        boolean b = true;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (b) {
                System.out.printf("Input number of points (3 <= index <= %d): ", maxN);
                try {
                    pNum = Integer.parseInt(br.readLine());
                    b = pNum <= 3 || pNum > maxN;
                    if (b) throw new Exception();
                } catch (Exception e) {
                    System.out.println("Illegal number format. Please try again.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pNum;
    }

    public static void main(String[] args) {
        // inputPointsNum(N): N - max count of points
        int N = inputPointsNum(100);//Integer.parseInt(args[0]);

        Polygon poly = new Polygon(N);

        // generate n random points
        for (int i = 0; i < N; i++) {
            int x = (int) (20 * Math.random());
            int y = (int) (20 * Math.random());
            poly.add(new Point(x, y));
        }

        // print initial array
        // System.out.println(poly);

        poly.sort();

        // print answer - sorted array
        System.out.println(poly);
    }
}
