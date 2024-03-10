# Multithreaded Matrix Multiplication in Java
This is a very efficient matrix multiplication calculator built using Java and its concurrency libraries. It can be used to multiply matrices populated using a random seed, but it can also be modified to use predefined values. The command line gives you the option to define the size of the matrices, which will be the same for both matrices, the seed used in random to populate the matrices, and the number of threads. The difference in efficiency using 1 thread can be compared to using more threads. However, there is diminishing returns past 15 threads, but this might depend on the number of cores available in the system. Some of the optimizations include:   
- Using a concurrent queue to efficiently use lock striping for better concurrency when accessing the queue; this means more threads can simultaneously access the tasks instead of having to block  
- Using a countdown latch to notify the main thread when all tasks are complete  
- Using tiling of tasks so each thread is operating on blocks of the matrix rather than individual cells; this helps reduce overhead of starting threads, blocking, memory and helpss with cache coherency  
- Flattening the 2D matrix to a 1D matrix so cells are contiguous and don't require accessing different sections of memory  
- Transposing the 2nd matrix so that the multiplication is done row by row; this means there are no hops when reading from each cell  
- Not locking the matrices since each thread is reading and writing to different cells  
- Creating the thread pool efficiently so that tasks are performed quicker  
    

## Project Structure
```
This package includes the following files.
|--src/main/java
    |--csx55.threads
        |--MatrixThreads.java [Used to start the main thread that creates the matrices, thread pools, and starts the operations]
        |--Matrix.java [Class used to organize each matrix; provides methods for adding, retrieving, transposing, and other functions]
        |--Task.java [Represents the block of cells within the resulting matrix that is added to the queue; performs multiplication]
        |--TaskQueue.java [A class that simply just extends a concurrent queue]
        |--Worker.java [Extends Thread and overrides run to poll a task from the queue and perform multiplication for that task]
|-- build.gradle [The file used to build this assignment]
|-- README.txt [This file]
|-- run.sh [A script that helps compile, run, and time the program using one command]
```

## Output
This project can be modified to do as many multiplications as needed, but the current implementation is being used to multiply three matrices together. A, B, C, and D are the initial matrices that are populated using the seed; this is done serially. The output is printed in the console for each multiplication that is done, which includes the time it took as well as the sum of each matrix for correctness. There will be sums of A, B, C, D, X (A dot B), Y (C dot D), and Z (X dot Y). Alongside this, there are times in seconds for the computation time and the overall time for all three operations.

## Dependencies
- Java 11
- Gradle 8.3

## Compiling
Clone the repository using:
```bash
$ git clone https://github.com/ashsProjects/Multithreaded_Matrix_Multiplication.git
```
Compile the Program:
```bash
$ gradle build
```
Change directories:
```bash
$ cd build/classes/java/main
```
Run the Program:
```bash
$ java csx55.threads.MatrixThreads <thread-pool-size> <matrix-dimension> <seed>
  For example: java csx55.threads.MatrixThreads 15 3000 42
```
To clean:
```bash
$ gradle clean
```
