# athenahealth-block-coding-puzzle

This was my submission to [athenahealth](https://www.athenahealth.com/) for a job interview in May 2012. I found the puzzle interesting enough to share!

[**Puzzle description**](athena%20Block%20Coding%20Puzzle-2012.pdf)

The answer it produces for the number of 48x10 panels that can be built within the parameters of the puzzle is 806844323190414 (806.8 trillion), computed in about 100 milliseconds when I measured it.

It was simple enough to come up with a brute force solution that worked for inputs up to 27x5, but far too slow for 48x10 - so I had to find a more creative approach.

## Run

    javac GarberBlockCalc.java
    java GarberBlockCalc 48 10
