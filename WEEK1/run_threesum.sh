#!/bin/bash
#usage "./run_threesum.sh <data_file>""
javac -cp .:../lib/algs4.jar ThreeSum.java
java -cp .:../lib/algs4.jar edu.princeton.cs.algs4.ThreeSum $1
