/* *****************************************************************************
 *  Name:              Shankar Ram
 *  Last modified:     17/07/2020
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites;
    private int size;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufBackwash;
    private int sourceNode;
    private int sinkNode;

    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("N must be greater than 0");
        size = n;
        int sizeSquared = n * n;
        grid = new boolean[size][size];
        uf = new WeightedQuickUnionUF(sizeSquared + 2);
        ufBackwash = new WeightedQuickUnionUF((sizeSquared + 1));
        sourceNode = sizeSquared;
        sinkNode = sizeSquared + 1;
        openSites = 0;

    }

    private int encode(int row, int col) {
        return size * (row - 1) + col - 1;
    }

    public void open(int row, int col) {

        validateSites(row, col);
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;

        int encodedIndex = encode(row, col);
        if (isOpen(row, col))
            return;

        grid[shiftedRow][shiftedCol] = true;
        openSites += 1;

        if (row == 1) {
            uf.union(encodedIndex, sourceNode);
            ufBackwash.union(encodedIndex, sourceNode);
        }
        if (row == size)
            uf.union(encodedIndex, sinkNode);

        // left
        if (chkSiteBounds(row, col - 1) && isOpen(row, col - 1)) {
            uf.union(encodedIndex, encode(row, col - 1));
            ufBackwash.union(encodedIndex, encode(row, col - 1));
        }
        // right
        if (chkSiteBounds(row, col + 1) && isOpen(row, col + 1)) {
            uf.union(encodedIndex, encode(row, col + 1));
            ufBackwash.union(encodedIndex, encode(row, col + 1));
        }
        // top
        if (chkSiteBounds(row - 1, col) && isOpen(row - 1, col)) {
            uf.union(encodedIndex, encode(row - 1, col));
            ufBackwash.union(encodedIndex, encode(row - 1, col));
        }
        // bottom
        if (chkSiteBounds(row + 1, col) && isOpen(row + 1, col)) {
            uf.union(encodedIndex, encode(row + 1, col));
            ufBackwash.union(encodedIndex, encode(row + 1, col));
        }
    }

    public boolean isOpen(int row, int col) {
        validateSites(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        validateSites(row, col);
        return ufBackwash.find(sourceNode) == ufBackwash.find(encode(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    private void validateSites(int row, int col) {
        if (!chkSiteBounds(row, col))
            throw new IndexOutOfBoundsException("Out of bounds");
    }

    private boolean chkSiteBounds(int row, int col) {
        int shiftedRow = row - 1;
        int shiftedCol = col - 1;
        return (shiftedRow >= 0 && shiftedRow < size && shiftedCol >= 0 && shiftedCol < size);

    }

    public boolean percolates() {
        return uf.find(sourceNode) == uf.find(sinkNode);
    }

    public static void main(String[] args) {

    }
}
