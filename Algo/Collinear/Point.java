/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    private class SlopeOrder implements Comparator<Point> {
      public int compare(Point p1, Point p2) {
        double slope1 = slopeTo(p1);
        double slope2 = slopeTo(p2);
        return Double.compare(slope1, slope2);
      }
    }
    
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
      if (that.x == Point.this.x) {
        if (that.y > Point.this.y) {
          return Double.POSITIVE_INFINITY;
        } else {
          return Double.NEGATIVE_INFINITY;
        }
      } else {
        if (that.y == Point.this.y)
          return 0.0;
        else
          return ((double) (that.y - Point.this.y)/(double) (that.x - Point.this.x));
      }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
      if (Point.this.y == that.y) {
        return Integer.compare(Point.this.x, that.x);
      } else {
        return Integer.compare(Point.this.y, that.y);
      }
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}