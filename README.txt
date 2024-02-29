README
======
HW2: SYNCHRONIZATION AND COORDINATION USING THREAD POOLS
Ayush Adhikari
CS455: Distributed Systems

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

To clean build:
  gradle clean

To build:
  gradle build

*After building, cd to build/classes/java/main, then run

To run program:
  java csx55.threads.MatrixThreads <thread-pool-size> <matrix-dimension> <seed>
  For example: java csx55.threads.MatrixThreads 15 3000 42