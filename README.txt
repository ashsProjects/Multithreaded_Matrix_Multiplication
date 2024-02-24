README
======
HW2: SYNCHRONIZATION AND COORDINATION USING THREAD POOLS
Ayush Adhikari
CS455: Distributed Systems

This package includes the following files.
|--src/main/java
    |--csx55.threads
        |--MatrixThreads.java []
        |--Matrix.java []
        |--Task.java []
        |--TaskQueue.java []
        |--Worker.java []
|-- build.gradle [The file used to build this assignment]
|-- README.txt [This file]

To clean build:
  gradle clean

To build:
  gradle build

*After building, cd to build/classes/java/main, then run

To run program:
  java csx55.threads.MatrixThreads <thread-pool-size> <matrix-dimension> <seed>
  For example: java csx55.threads.MatrixThreads 15 1000 42