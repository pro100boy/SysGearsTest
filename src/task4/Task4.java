package task4;

import java.lang.Math;
import java.util.Random;

public class Task4 {
    //using in generating test cases
    private static int MAX_VALUE = 12;
    private static int MID_X = 0;
    private static int MID_Y = 0;

    public static void main(String[] args) {
        Point[] array = generatePointArray();
        System.out.println("Points length " + array.length);
        for (int i = 0; i < array.length; i++) {
            array[i].print();
        }

        //find conventional center
        Point[] centerArray = findCenterMovePoints(array);
        array = findSequenceOfPoints(centerArray);
        //Print answer
        for (int i = 0; i < array.length; i++) {
            array[i].print();
        }

    }
    /**
     * Find the right sequence of points. Answer.
     * @return - result array[]
     */
    private static Point[] findSequenceOfPoints(Point[] centerArray) {
        Point[] res = new Point[centerArray.length];
        //add polarAngle to points
        for (int i = 0; i < centerArray.length; i++) {
            double h = polarAngle(centerArray[i]);
            centerArray[i].setAngle(h);
        }
        double minAngle = 100;
        for (int i = 0; i < centerArray.length; i++) {
            int k = 0;
            for (int j = 0; j < centerArray.length; j++) {
                if (minAngle >= centerArray[j].getAngle()) {
                    minAngle = centerArray[j].getAngle();
                    k = j;
                }
            }
            res[i] = centerArray[k];
            centerArray[k].setAngle(100.0);
            minAngle = 100;
        }
        System.out.print("BLA");
        //Move points back to their place
        for (int i = 0; i < res.length; i++) {
            Point p = new Point(res[i].x + MID_X, res[i].y + MID_Y);
            res[i] = p;
        }

        return res;
    }

    /**
     * Calculating polar angle for point
     * @return - double - angle
     */
    static double polarAngle(Point p) {
        double alpha = Math.atan2(p.y, p.x);
        if (alpha < 0) alpha += 2 * 3.14;
        return alpha;
    }

    /**
     * Find conventional center of figure and moving figure on MIN_X and MIN_Y
     * @return - array of Points
     */
    private static Point[] findCenterMovePoints(Point[] array) {
        Point[] res = new Point[array.length];
        //first find center
        int x = 0;
        int y = 0;
        for (int i = 0; i < array.length; i++) {
            x += array[i].x;
            y += array[i].y;
        }
        System.out.println("mid x - " + x + " mid Y - " + y);
        x = x / array.length;
        y = y / array.length;
        MID_X = x;
        MID_Y = y;
        System.out.println("mid x - " + x + " mid Y - " + y);
        //in case if point will be in MID_X,MID_Y
        Point test = new Point(MID_X,MID_Y);
        for (int i = 0; i < array.length; i++) {
            if (test == array[i]){
                System.out.println("REBILD mid x - " + MID_X + " mid Y - " + MID_Y);
                MID_Y++;
                MID_X++;
            }
        }
        //move points
        for (int i = 0; i < array.length; i++) {
            Point p = new Point(array[i].x - x, array[i].y - y);
            res[i] = p;
        }
        return res;

    }

    /**
     * Generate points to test algorithm
     * @return - array of Points
     */
    private static Point[] generatePointArray() {
        Random random = new Random();
        int pointsQuantity = random.nextInt(MAX_VALUE);
        while (pointsQuantity<8){
            pointsQuantity = random.nextInt(MAX_VALUE);
        }
        Point[] array = new Point[pointsQuantity];
        for (int i = 0; i < pointsQuantity; i++) {
            array[i] = new Point(random.nextInt(16), random.nextInt(16));
        }
        return array;
    }


    static class Point {
        int x;
        int y;
        double angle;

        public double getAngle() {
            return angle;
        }

        public void setAngle(double angle) {
            this.angle = angle;
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void print() {
            System.out.print("\"" + x + ", " + y + "\". ");
        }

    }
}
