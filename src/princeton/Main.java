package princeton;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created by User on 21.05.2017.
 * https://cs.stackexchange.com/a/52627
 */
public class Main {
    private static void printArr(Point[] points) {
        System.out.println(Arrays.stream(points).map(i -> i.toString()).collect(Collectors.joining(" ")));
    }

    private static Point[] sortByYminXmax(Point[] points) {
        Comparator<Point> comparator = (o1, o2) -> {
            int res = Integer.compare(o1.y, o2.y);
            if (res == 0) res = Integer.compare(o2.x, o1.x);
            return res;
        };

        Arrays.sort(points, comparator);
        return points;
    }

    // is p0->a->b a counter-clockwise turn?
    // +1 if counter-clockwise, -1 if clockwise, 0 if collinear
    public static int ccw(Point p0, Point a, Point b) {
        double area2 = (a.x - p0.x) * (b.y - p0.y) - (b.x - p0.x) * (a.y - p0.y);
        if (area2 < 0) return -1;
        else if (area2 > 0) return +1;
        else return 0;
    }

    public static int distCompare(Point p0, Point a, Point b) {
        int distA = (p0.x - a.x) * (p0.x - a.x) + (p0.y - a.y) * (p0.y - a.y);
        int distB = (p0.x - b.x) * (p0.x - b.x) + (p0.y - b.y) * (p0.y - b.y);
        return distA - distB;
    }

    public static int angleCompare(Point p0, Point a, Point b) {
        int left = ccw(p0, a, b);
        if (left == 0) return distCompare(p0, a, b);
        return left;
    }

    private static Point[] sortByAngle(Point[] points) {
        Comparator<Point> compareByAngle = (o1, o2) -> angleCompare(points[0], o1, o2);
        Arrays.sort(points, compareByAngle);
        return points;
    }

    public static void main(String[] args) {
        int pCnt = 20;
        Point[] p = new Point[pCnt];
        for (int i = 0; i < pCnt; i++) {
            int x = (int) (20 * Math.random());
            int y = (int) (20 * Math.random());
            p[i] = new Point(x, y);
        }

        // print initial array
        printArr(p);

        sortByYminXmax(p);
        sortByAngle(p);

        // print answer - sorted array
        printArr(p);
    }
}
