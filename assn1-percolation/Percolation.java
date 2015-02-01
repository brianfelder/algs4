public class Percolation {
    private int top, bottom;
    private WeightedQuickUnionUF unionFind;
    private int gridWidth;

    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        gridWidth = N;
        unionFind = new WeightedQuickUnionUF(N^2);
    }
    
    private int indexFor(int i, int j) {
        if ( (! (1 < i && i < gridWidth)) ||
             (! (1 < j && j < gridWidth)))
            return -1;
        return ((i - 1) * gridWidth) + (j - 1);
    }
    
    /**
     * open site (row i, column j) if it is not open already
     */
    public void open(int i, int j) {
        int gridIndex = indexFor(i, j);
        if (gridIndex == -1)
            throw new IndexOutOfBoundsException();
        
    }
    
    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return false;
    }
    
    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        return false;
    }
    
    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return false;
    }
   
    /**
     * test client (optional)
     */
    public static void main(String[] args) {
    }
}
