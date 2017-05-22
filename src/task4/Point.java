package task4;

/******************************************************************************
 *  Compilation:  javac Point.java
 *  Implementation of 2D point using rectangular coordinates.
 *
 ******************************************************************************/

public class Point {
    public final int x;
    public final int y;
    private static int cnt = 0;
    private int i = 0;

    // create and initialize a point with given (x, y)
    public Point(int x, int y) {
        this.i = cnt++;
        this.x = x;
        this.y = y;
    }

    // return string representation of this point
    public String toString() {
        return String.format("P%d(%d, %d) ", i, x, y);
    }
}

