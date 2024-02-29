package csx55.threads;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MatrixThreads {
    static CountDownLatch latch;
    public static void main(String[] args) throws InterruptedException {
        Matrix A, B, C, D, X, Y, Z;
        TaskQueue queue;
        Thread[] threads;
        Random rand;

        int threadPoolSize = 0;
        int matrixDimension = 0;
        int seed = 0;
        int blockSize = 1;

        long startTime, endTime;
        double xTime, yTime, zTime, totalTime;
        final double BILLION = 1_000_000_000.00;

        if (args.length < 3) {
            System.out.println("Incorrect number of arguments provided");
            System.exit(1);
        }

        try {
            threadPoolSize = Integer.parseInt(args[0]);
            matrixDimension = Integer.parseInt(args[1]);
            seed = Integer.parseInt(args[2]);
            blockSize = (int) Math.ceil(matrixDimension * 0.1);
            latch = new CountDownLatch(blockSize * 100);

            //check for valid matrixDimension and pool size
            if (threadPoolSize < 1) {
                System.out.println("Thread pool size must be greater than 0");
                System.exit(1);
            }
            if (matrixDimension < 1) {
                System.out.println("Dimension of matrices must be greater than 0");
                System.exit(1);
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Arguments provided are not integers");
            System.exit(1);
        }
        System.out.println("Dimensionality of the square matrices is: " + matrixDimension);
        System.out.println("The thread pool size has been initialized to: " + threadPoolSize);
        System.out.println();

        //initialize Matrices with random seed and dimension
        rand = new Random(seed);
        A = new Matrix(matrixDimension, rand);
        B = new Matrix(matrixDimension, rand);
        C = new Matrix(matrixDimension, rand);
        D = new Matrix(matrixDimension, rand);
        X = new Matrix(matrixDimension);
        Y = new Matrix(matrixDimension);
        Z = new Matrix(matrixDimension);

        System.out.println("Sum of the elements in input matrix A = " + A.getSum());
        System.out.println("Sum of the elements in input matrix B = " + B.getSum());
        System.out.println("Sum of the elements in input matrix C = " + C.getSum());
        System.out.println("Sum of the elements in input matrix D = " + D.getSum());
        System.out.println();

        //create queue and start thread pool
        queue = new TaskQueue();
        threads = new Worker[threadPoolSize];
        for (int i = 0; i < threadPoolSize; i++) {
            threads[i] = new Worker(queue);
            threads[i].start();
        }

        //compute X using A and B
        B.transpose();
        startTime = System.nanoTime();
        for (int i = 0; i < matrixDimension; ++i) {
            for (int j = 0; j < matrixDimension; j+=blockSize) {
                queue.offer(new Task(A, B, X, i, j, blockSize));
            }
        }
        latch.await();
        endTime = System.nanoTime();
        xTime = (endTime - startTime) / BILLION;
        System.out.println("Calculation of matrix X (product of A and B) complete - sum of the elements in X is : " + X.getSum());
        System.out.println(String.format("Time to compute matrix X: %.3f s\n", xTime));

        //compute Y using C and D
        D.transpose();
        latch = new CountDownLatch(blockSize * 100);
        startTime = System.nanoTime();
        for (int i = 0; i < matrixDimension; ++i) {
            for (int j = 0; j < matrixDimension; j+=blockSize) {
                queue.offer(new Task(C, D, Y, i, j, blockSize));
            }
        }
        latch.await();
        endTime = System.nanoTime();
        yTime = (endTime - startTime) / BILLION;
        System.out.println("Calculation of matrix Y (product of C and D) complete - sum of the elements in Y is : " + Y.getSum());
        System.out.println(String.format("Time to compute matrix Y: %.3f s\n", yTime));

        //compute Z using X and Y
        Y.transpose();
        latch = new CountDownLatch(blockSize * 100);
        startTime = System.nanoTime();
        for (int i = 0; i < matrixDimension; ++i) {
            for (int j = 0; j < matrixDimension; j+=blockSize) {
                queue.offer(new Task(X, Y, Z, i, j, blockSize));
            }
        }
        latch.await();
        endTime = System.nanoTime();
        zTime = (endTime - startTime) / BILLION;
        System.out.println("Calculation of matrix Z (product of X and Y) complete - sum of the elements in Z is : " + Z.getSum());
        System.out.println(String.format("Time to compute matrix Z: %.3f s\n", zTime));

        totalTime = xTime + yTime + zTime;
        System.out.println(String.format("Cumulative time to compute matrices X, Y, and Z using a thread pool of size = %d is: %.3f s", matrixDimension, totalTime));

        //end of program
        //interrupt threads and wait
        for (Thread t: threads) {
            t.interrupt();
            t.join();
        }
    }
}