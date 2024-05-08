#!/bin/sh
gradle clean
gradle build

cd ~/Documents/cs455/Assign2/build/classes/java/main
time java csx55.threads.MatrixThreads 15 3000 31459
