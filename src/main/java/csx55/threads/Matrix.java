package csx55.threads;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private int[] matrix;
    private int size;

    public Matrix(int size) {
        this.size = size;
        matrix = new int[size * size];
    }

    public Matrix(int size, Random rand) {
        this.size = size;
        matrix = new int[size * size];
        for (int i = 0; i < matrix.length; ++i) {
            matrix[i] = 1000 - rand.nextInt(2000);
        }
    }

    public int get(int i, int j) {
        int index = (i * size) + j;
        return matrix[index];
    }

    public void add(int i, int j, int value) {
        int index = (i * size) + j;
        matrix[index] += value;
    }

    public long getSum() {
        long sum = 0L;
        for (int i = 0; i < matrix.length; ++i) {
            sum += matrix[i];
        }
        return sum;
    }

    public int getSize() {
        return this.size;
    }

    public String toString() {
        return Arrays.toString(matrix);
    }

    public void transpose() {
        int[] transposed = new int[size * size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                transposed[i * size + j] = this.get(j, i);
            }
        }
        this.matrix = transposed;
    }
}
