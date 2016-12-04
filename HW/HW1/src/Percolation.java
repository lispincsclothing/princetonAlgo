/**
 * Created by student on 11/6/16.
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    private int gridSizeInt;
    private int[] sites;

    public Percolation(int rowSizeInt)                // create n-by-n grid, with all sites blocked
    {
        if (rowSizeInt <= 0){
            throw new IllegalArgumentException("N must be greater than 0.");
        }

        gridSizeInt = (rowSizeInt^2) + 2; // grid stored in single array, squared, plus the top and bottom virtual sites

        //Standary declaration and initialization - same as QuickUnionUF
        sites = new int[gridSizeInt];
        for (int i = 0; i < gridSizeInt ; i++) {
            sites[i]=i;
        }
    }


    public void open(int row, int col)       // open site (row, col) if it is not open already
    {

    }
    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        return true;
    }
    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        return true;
    }
    public boolean percolates()              // does the system percolate?
    {
        return true;
    }
    public static void main(String[] args)   // test client (optional)
    {

    }
}
