public class Percolation {
    private int topVirtualParentIndex, bottomVirtualParentIndex;
    private WeightedQuickUnionUF unionFind;
    private int gridWidth;
    private int arraySize;
    private boolean[] isSpaceOpen;
    private final static int OUT_OF_BOUNDS_INDEX = -1;

    /**
     * create N-by-N grid, with all sites blocked
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();
        gridWidth = N;
        arraySize = (int)Math.round(Math.pow(N, 2)) + 2; // 0 for top virtual parent, last for bottom virtual parent
        unionFind = new WeightedQuickUnionUF(arraySize);
        // Initialize the isOpen array; values will be false to begin.
        isSpaceOpen = new boolean[arraySize];

        // Set up the virtual top and virtual bottom sites.
        topVirtualParentIndex = 0;
        bottomVirtualParentIndex = arraySize - 1;

        // Open the virtual parents.
        isSpaceOpen[topVirtualParentIndex] = true;
        isSpaceOpen[bottomVirtualParentIndex] = true;


    }
    
    private int indexFor(int i, int j) {
        if (j < 1 || j > gridWidth)
            return OUT_OF_BOUNDS_INDEX;
        else if (i < 1)
            return topVirtualParentIndex;
        else if (i > gridWidth)
            return bottomVirtualParentIndex;
        return ((i - 1) * gridWidth) + j;
    }

    /**
     * Return the list of neighbor indexes for any space on the grid.
     * @param i row (1-N)
     * @param j column (1-N)
     * @return 4 indexes in the array representing adjacent indexes.
     *         Values off the grid are of value -1.
     */
    private int[] neighborIndexes (int i, int j) {
        int[] toReturn = new int[4];
        toReturn[0] = indexFor(i-1, j); // one up
        toReturn[1] = indexFor(i+1, j); // one down
        toReturn[2] = indexFor(i, j-1); // one left
        toReturn[3] = indexFor(i, j+1); // one right
        return toReturn;
    }
    
    /**
     * open site (row i, column j) if it is not open already
     */
    public void open(int i, int j) {
        int gridIndex = indexFor(i, j);
        if (gridIndex == OUT_OF_BOUNDS_INDEX)
            throw new IndexOutOfBoundsException();
        // If we're already open, nothing to do.
        if (isSpaceOpen[gridIndex])
            return;

        // Mark this space as open.
        isSpaceOpen[gridIndex] = true;

        // Connect to neighbor indexes.
        int [] neighbors = neighborIndexes(i, j);
        for (int index = 0; index < neighbors.length; index++) {
            // connect to neighbors within the grid.
            if ((neighbors[index] != OUT_OF_BOUNDS_INDEX) &&
                    (isSpaceOpen[neighbors[index]])) {
                unionFind.union(gridIndex, neighbors[index]);
            }
        }
    }
    
    /**
     * is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        int gridIndex = indexFor(i, j);
        if (gridIndex == OUT_OF_BOUNDS_INDEX)
            throw new IndexOutOfBoundsException();
        // System.out.println("isOpen -- " + i + " " + j + " arraySize: " + arraySize + " gridIndex: " + gridIndex);
        return isSpaceOpen[gridIndex];
    }
    
    /**
     * is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        int gridIndex = indexFor(i, j);
        if (gridIndex == OUT_OF_BOUNDS_INDEX)
            throw new IndexOutOfBoundsException();
        // System.out.println("isFull -- " + i + " " + j + " arraySize: " + arraySize + " gridIndex: " + gridIndex);
        return unionFind.connected(topVirtualParentIndex, gridIndex);
    }
    
    /**
     * does the system percolate?
     */
    public boolean percolates() {
        //System.out.println("percolates: " + topVirtualParentIndex + " " + bottomVirtualParentIndex
        //         + " " + unionFind.find(topVirtualParentIndex) + " " + unionFind.find(bottomVirtualParentIndex));
        return unionFind.connected(topVirtualParentIndex, bottomVirtualParentIndex);
    }
   
    /**
     * test client (optional)
     */
    public static void main(String[] args) {
    }
}
