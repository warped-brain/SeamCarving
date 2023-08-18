public class SparseMatrix {
    private int numRows;
    private int numCols;
    private int[][] M;
    private int nonZeroCount;

    public SparseMatrix(int numRows, int numCols,int nonZero) {
        this.numRows = numRows;
        this.numCols = numCols;

        this.M = new int[3][nonZero];
        this.nonZeroCount = 0;
    }

    public void setValue(int row, int col, int value) {
        if (value != 0) {
            M[0][nonZeroCount] = row;
            M[1][nonZeroCount] = col;
            M[2][nonZeroCount] = value;
            nonZeroCount++;
        }
    }

    public int getValue(int row, int col) {
        for (int i = 0; i < nonZeroCount; i++) {
            if (M[0][i] == row && M[1][i] == col) {
                return M[2][i];
            }
        }
        return 0;
    }

    public int getNumCols() {
        return this.numCols;
    }

    public int getNumRows() {
        return this.numRows;
    }
}
