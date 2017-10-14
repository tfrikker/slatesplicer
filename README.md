# slatesplicer
Tufts Polyhack 2017


FindSlate.java

java FindSlate <input> <sensitivity> <range>
* input: an audio file in .wav format
* sensitivity: a floating point value. Determines the margin by which a new peak
    must exceed an old peak.
* range: an integer representing the number of seconds into the file the slate
    clap can possibly be. The smaller the range specified, the cleaner the results.
