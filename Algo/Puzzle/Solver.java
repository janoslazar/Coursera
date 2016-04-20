import java.util.Comparator;
import java.util.Iterator;

public class Solver {
  private int move;
  private Node lastNode;

  public Solver(Board initial) {
   if (initial == null)
     throw new java.lang.NullPointerException();
   
   this.move = -1;
   this.lastNode = null;

   MinPQ<Node> pqNodes = initSearchNode(initial);
   Board twin = initial.twin();
   MinPQ<Node> pqTwin = initSearchNode(twin);
   while (!pqNodes.isEmpty() && !pqTwin.isEmpty()) {
     Node minNode = pqNodes.delMin();
     Node twinMin = pqTwin.delMin();

     if (minNode.board.isGoal()) {
       this.move = minNode.moves;
       this.lastNode = minNode;
       return;
     } else if (twinMin.board.isGoal()) {
       this.move = -1;
       this.lastNode = null;
       return;
     } else {
       for (Board board : minNode.board.neighbors()) {
         Node node = new Node();
         node.board = board;
         node.moves = minNode.moves + 1;
         node.prev = minNode;
         Node backwardNode = node.prev;
         boolean found = false;
         while (backwardNode != null) {
           if (node.board.equals(backwardNode.board)) {
             found = true;
           }
           backwardNode = backwardNode.prev;
         }
         if (!found)
           pqNodes.insert(node);
       }
       for (Board board : twinMin.board.neighbors()) {
         Node node = new Node();
         node.board = board;
         node.moves = minNode.moves + 1;
         node.prev = minNode;
         Node backwardNode = node.prev;
         boolean found = false;
         while (backwardNode != null) {
           if (node.board.equals(backwardNode.board)) {
             found = true;
           }
           backwardNode = backwardNode.prev;
         }
         if (!found)
           pqTwin.insert(node);
       }
     }
   }
 }
  
  private class Node {
    private Board board;
    private int moves;
    private Node prev;
  }

  private class NodeComp implements Comparator<Node> {
    public int compare(Node node1, Node node2) {
      int priority1 = node1.board.manhattan() + node1.moves;
      int priority2 = node2.board.manhattan() + node2.moves;
      if (priority1 < priority2)
        return -1;
      else if (priority1 == priority2)
        return 0;
      else
        return 1;
    }
 }

  private MinPQ<Node> initSearchNode(Board init) {
    NodeComp comp = new NodeComp();
    MinPQ<Node> pqNodes = new MinPQ<Node>(1, comp);
    Node initNode = new Node();
    initNode.board = init;
    initNode.moves = 0;
    initNode.prev = null;
    pqNodes.insert(initNode);
    return pqNodes;
 }

 public boolean isSolvable() {
  return (move != -1);
 }

 public int moves() {
  return move;
 }

 private class SolutionIterator implements Iterator<Board> {
   private Stack<Board> solutionSequence;

   private SolutionIterator() {
     solutionSequence = new Stack<Board>();
     Node curSN = lastNode;
     while (curSN != null) {
       solutionSequence.push(curSN.board);
       curSN = curSN.prev;
     }
   }

   public boolean hasNext() {
     return (!solutionSequence.isEmpty());
   }

   public Board next() {
     return solutionSequence.pop();
   }

   public void remove() {
   }
 }

 private class SolutionIterable implements Iterable<Board> {
   public Iterator<Board> iterator() {
     return new SolutionIterator();
   }
 }

 public Iterable<Board> solution() {
   if (!isSolvable())
     return null;
   return new SolutionIterable();
 }

  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
          StdOut.println(board);
    }
  }  // solve a slider puzzle (given below)
}