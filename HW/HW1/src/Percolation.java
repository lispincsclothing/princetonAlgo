/**
 * Created by student on 11/6/16.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private WeightedQuickUnionUF union;
    private WeightedQuickUnionUF backwash;
    private boolean[] openSites;
    private final int SIZE, TOP_INDEX, BOTTOM_INDEX;


    public Percolation(int rowSizeInt)                // create n-by-n grid, with all sites blocked
    {
        if (rowSizeInt <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }

        // grid stored in single array, squared, plus the top and bottom virtual sites
        int numNodes = rowSizeInt * rowSizeInt + 2;

        union = new WeightedQuickUnionUF(numNodes);
        backwash = new WeightedQuickUnionUF(numNodes);
        openSites = new boolean[numNodes];
        SIZE = rowSizeInt;
        //Q: Why are TOP and BOTTOM index assigned thusly?
        TOP_INDEX = SIZE * SIZE;
        BOTTOM_INDEX = SIZE * SIZE + 1;
    }

    public void open(int row, int column)       // open site (row, col) if it is not open already
    {
        assertSiteInRange(row, column);
        openSites[toIndex(row, column)] = true;
        connectToVirtualTopNode(row, column);
        connecToAdjacentNodes(row, column);
        connectToVirtualBottomNode(row, column);
    }

    private void assertSiteInRange(int row, int column){
        if (row < 1 || row > SIZE || column < 1 || column > SIZE) {
            throw new IndexOutOfBoundsException("Row or column is out of range");
        }
    }

    private int toIndex(int row, int column){
        //-1 since index starts at 0, * SIZE since going down rows, column since column offset
        return (row - 1) * SIZE + (column - 1);
    }

    private void connectToVirtualTopNode(int row, int column){
        if (row == 1){
            union(TOP_INDEX, toIndex(row, column));
        }
    }

    private void union(int p, int q){
        union.union(p, q);
        backwash.union(p, q);
    }

    private void connecToAdjacentNodes(int row, int column){
        connectTopNode(row, column);
        connectBottomNode(row, column);
        connectLeftNode(row, column);
        connectRightNode(row, column);
    }

    private void connectTopNode(int row, int column){
        if(row > 1 && isOpen(row - 1, column)){
            union(toIndex(row-1, column), toIndex(row, column));
        }
    }

    private void connectBottomNode(int row, int column){
        if(row < SIZE && isOpen(row + 1, column)){
            union(toIndex(row + 1, column), toIndex(row, column));
        }
    }

    private void connectLeftNode(int row, int column){
        if(column > 1  && isOpen(row, column - 1)){
            union(toIndex(row, column - 1), toIndex(row, column));
        }
    }

    private void connectRightNode(int row, int column){
        if(column < SIZE && isOpen(row, column + 1)){
            union(toIndex(row, column + 1), toIndex(row, column));
        }
    }

    private void connectToVirtualBottomNode(int row, int column) {
        if (row == SIZE)
        {
            backwash.union(BOTTOM_INDEX, toIndex(row, column));
        }
    }

    public boolean isOpen(int row, int column)  // is site (row, col) open?
    {
        assertSiteInRange(row, column);
        return openSites[toIndex(row, column)];
    }

    public boolean isFull(int row, int column)  // is site (row, col) full?
    {
        assertSiteInRange(row, column);
        return union.connected(toIndex(row, column), TOP_INDEX);
    }

    public boolean percolates()              // does the system percolate?
    {
        return backwash.connected(BOTTOM_INDEX, TOP_INDEX);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
