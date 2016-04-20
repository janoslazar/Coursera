import java.util.Arrays;

public class Brute {
  private static boolean collinear(Point p1, Point p2, Point p3, Point p4) {
    return Double.compare(p1.slopeTo(p2), p1.slopeTo(p3)) == 0
        && Double.compare(p1.slopeTo(p3), p1.slopeTo(p4)) == 0;
  }
  
  private static void printLine(Point p1, Point p2, Point p3, Point p4) {
    Point[] p = new Point[4];
    p[0] = p1;
    p[1] = p2;
    p[2] = p3;
    p[3] = p4;
    Arrays.sort(p);
    StdOut.print(p[0] + " -> " + p[1] + " -> " + p[2] + " -> " + p[3]);
    StdOut.println();
    p[0].draw();
    p[1].draw();
    p[2].draw();
    p[3].draw();
    p[0].drawTo(p[3]);
  }
  
  public static void main(String[] args) {
    if (args.length < 1) return;
    
    In input = new In(args[0]);
    
    int N = input.readInt();
    if (N < 4) return;
    
    Point[] points = new Point[N];
    for (int i = 0; i < N; ++i) {
      int x = input.readInt();
      int y = input.readInt();
      points[i] = new Point(x, y);
    }
    
    for (int i = 0; i < N; ++i) {
      Point p1 = points[i];
      for (int j = i + 1; j < N; ++j) {
        Point p2 = points[j];
        for (int k = j + 1; k < N; ++k) {
          Point p3 = points[k];
          for (int l = k + 1; l < N; ++l) {
            Point p4 = points[l];
            if (collinear(p1, p2, p3, p4)) {
              printLine(p1, p2, p3, p4);
            }
          }
        }
      }
    }
  }
}