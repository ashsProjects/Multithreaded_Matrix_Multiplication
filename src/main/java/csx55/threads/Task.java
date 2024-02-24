package csx55.threads;

public class Task {
    private Matrix A;
    private Matrix B;
    private Matrix result;
    private int row;
    private int col;
    private int block;

    public Task(Matrix A, Matrix B, Matrix C, int row, int col, int block) {
        this.A = A;
        this.B = B;
        this.result = C;
        this.row = row;
        this.col = col;
        this.block = block;
    }

    public void multiply() {
        int endIndex = col + block;
        for (int b = col; b < endIndex; ++b) {
            for (int k = 0; k < A.getSize(); ++k) {
                int val = A.get(row, k) * B.get(b, k);
                result.add(row, b, val);
            }
        }   
    }
}
