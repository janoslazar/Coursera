import java.util.Iterator;

public class Board {
  private int N;
  private int[][] board;
  private int blankI;
  private int blankJ;
  private int manDist;
  
  public Board(int[][] blocks) {
    N = blocks.length;
    manDist = -1;
    board = new int[N][N];
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        board[i][j] = blocks[i][j];
        if (board[i][j] == 0) {
          blankI = i;
          blankJ = j;
        }
      }
    }
  }
   
  public int dimension() {
    return N;
  }
  
  public int hamming() {
    int dist = 0;
    int should = 1;
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (board[i][j] != should && board[i][j] != 0)
          ++dist;
        ++should;
      }
    }
    return dist;
  }
  
  public int manhattan() {
    if (manDist != -1)
      return manDist;
    int dist = 0;
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        int actualNum = board[i][j];
        if (actualNum != 0) {
          int rowIdx = (actualNum - 1) / N;
          int colIdx = (actualNum - 1) % N;
          dist += Math.abs(i - rowIdx);
          dist += Math.abs(j - colIdx);
        }
      }
    }
    manDist = dist;
    return manDist;
  }
  
  public boolean isGoal() {
    int should = 1;
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (board[i][j] != should && board[i][j] != 0)
          return false;
        ++should;
      }
    }
    return true;
  }
  
  public Board twin() {
    Board twin = new Board(this.board);
    if (twin.board[0][0] != 0 && twin.board[0][1] != 0) {
      int temp = twin.board[0][0];
      twin.board[0][0] = twin.board[0][1];
      twin.board[0][1] = temp;
    } else {
      int temp = twin.board[1][0];
      twin.board[1][0] = twin.board[1][1];
      twin.board[1][1] = temp;
    }
    return twin;
  }
  
  public boolean equals(Object y) {
    if (y == this) return true;
    if (y == null) return false;
    if (y.getClass() != this.getClass()) return false;
    Board that = (Board) y;
    if (this.board.length != that.board.length) return false;
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        if (this.board[i][j] != that.board[i][j])
          return false;
      }
    }
    return true;
  }
  
  private class NeighborIterator implements Iterator<Board> {
    private Queue<Board> neighbors;

    public NeighborIterator() {
      neighbors = new Queue<Board>();
      if (blankI > 0) {
        Board leftNeighbor = new Board(board);
        leftNeighbor.board[blankI][blankJ] = leftNeighbor.board[blankI - 1][blankJ];
        leftNeighbor.board[blankI - 1][blankJ] = 0;
        --leftNeighbor.blankI;
        neighbors.enqueue(leftNeighbor);
      }
      if (blankI < N - 1) {
        Board rightNeighbor = new Board(board);
        rightNeighbor.board[blankI][blankJ] = 
          rightNeighbor.board[blankI + 1][blankJ];
        rightNeighbor.board[blankI + 1][blankJ] = 0;
        ++rightNeighbor.blankI;
        neighbors.enqueue(rightNeighbor);
      }
      if (blankJ > 0) {
        Board upperNeighbor = new Board(board);
        upperNeighbor.board[blankI][blankJ] = 
          upperNeighbor.board[blankI][blankJ - 1];
        upperNeighbor.board[blankI][blankJ - 1] = 0;
        --upperNeighbor.blankJ;
        neighbors.enqueue(upperNeighbor);
      }
      if (blankJ < N - 1) {
        Board downNeighbor = new Board(board);
        downNeighbor.board[blankI][blankJ] = downNeighbor.board[blankI][blankJ + 1];
        downNeighbor.board[blankI][blankJ + 1] = 0;
        ++downNeighbor.blankJ;
        neighbors.enqueue(downNeighbor);
      }
  }

  public boolean hasNext() {
    return (!neighbors.isEmpty());
  }
    
  public Board next() {
    return neighbors.dequeue();
  }
    
  public void remove() {      
  }
}

  private class NeighborIterable implements Iterable<Board> {
    public Iterator<Board> iterator() {
      NeighborIterator iter = new NeighborIterator();
      return iter;
    }
  }

  public Iterable<Board> neighbors() {
    Iterable<Board> iter = new NeighborIterable();
    return iter;
  }

  public String toString() {
    String output = "";
    output += N + "\n";
    for (int i = 0; i < N; ++i) {
      for (int j = 0; j < N; ++j) {
        output += " " + board[i][j];
      }
      output += "\n";
    }
    
    return output;
  }

  public static void main(String[] args) {
    int N = 3;
    int[][] blocks = new int[N][N];

    blocks[0][0] = 1;
    blocks[0][1] = 2;
    blocks[0][2] = 3;
    blocks[1][0] = 4;
    blocks[1][1] = 5;
    blocks[1][2] = 6;
    blocks[2][0] = 7;
    blocks[2][1] = 8;
    blocks[2][2] = 0;
    
    Board b = new Board(blocks);
    StdOut.println(b + "\nhamming : " + b.hamming() + "\nmanhattan: "
                     + b.manhattan() + "\nGoal: " + b.isGoal());
    
    for (Board board : b.neighbors()) {
          StdOut.println("------------------");
          StdOut.println(board + "\nhamming : " + board.hamming() + "\nmanhattan: "
                + board.manhattan() + "\nGoal: " + board.isGoal());
    }
    
    StdOut.println(b.twin());
  }
}