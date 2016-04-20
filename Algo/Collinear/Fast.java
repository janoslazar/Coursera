import java.util.Arrays;
  
public class Fast {
  private static void drawPoints(Point[] sorted, Point startPoint, 
                                 int startIdx, int adj) {
    Point[] seg = new Point[adj + 1];
    for (int i = 0; i < adj; ++i) {
      seg[i] = sorted[startIdx + i];
    }
    seg[adj] = startPoint;
    Arrays.sort(seg);
    
    if (startPoint.compareTo(seg[0]) == 0) {
      String output = seg[0].toString();
      seg[0].draw();
      for (int i = 1; i < seg.length; ++i) {
        seg[i].draw();
        output += " -> " + seg[i].toString();
      }
      seg[0].drawTo(seg[seg.length - 1]);
      StdOut.println(output);
    }
  }
  
  private static void findAdj(Point p, Point[] sorted) {
    double lastSlope = p.slopeTo(sorted[0]);
    int startIdx = 0;
    int adj = 1;
    
    for (int i = 1; i < sorted.length; ++i) {
      double currSlope = p.slopeTo(sorted[i]);
      if (Double.compare(currSlope, lastSlope) == 0) {
        ++adj;
      } else if (p.compareTo(sorted[i]) == 0) {
        continue;
      } else if (adj >= 3) {
        drawPoints(sorted, p, startIdx, adj);
        lastSlope = currSlope;
        startIdx = i;
        adj = 1;
      } else {
        lastSlope = currSlope;
        startIdx = i;
        adj = 1;
      }
    }

    if (adj >= 3) {
      drawPoints(sorted, p, startIdx, adj);
    }
  }
  
  public static void main(String[] args) {
    if (args.length < 1) return;
    
    In input = new In(args[0]);
    
    int N = input.readInt();
    if (N < 4) return;
    
    Point[] points = new Point[N];
    Point[] sorted = new Point[N];
    for (int i = 0; i < N; ++i) {
      int x = input.readInt();
      int y = input.readInt();
      points[i] = new Point(x, y);
      sorted[i] = points[i];
    }
    
    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);

    for (int i = 0; i < N; ++i) {
     Point p = points[i];
     Arrays.sort(sorted, p.SLOPE_ORDER);
     findAdj(p, sorted);
    }
  }
}