#!/bin/sh
cd ~/Documents/cs455/Assign2

if module list 2>&1 | grep -q "courses/cs455"; then :
else
    module unload courses
    module load courses/cs455
fi

gradle clean
gradle build

cd ~/Documents/cs455/Assign2/build/classes/java/main
time java csx55.threads.MatrixThreads 15 3000 31459