# athenahealth-block-coding-puzzle

This was my submission to athenahealth for a job interview in May 2012. I found the puzzle interesting enough to share!

The answer it produces for the number of 48x10 panels that can be built within the parameters of the puzzle is 806844323190414 (806.8 trillion), computed within 100 milliseconds.

**Puzzle description**: athena Block Coding Puzzle-2012.pdf

Notably, I've never used Java professionally and hadn't written it since 2006 until this assignment! However, athena wouldn't accept a submission using my choices at the time, C# or VB.NET.

It was relatively simple to come up with an initial solution that produced the right answers to inputs up to 27x5, but was far too slow to arrive at the answer to the number of 48x10 panels that can be built. The final solution required coming up with an interesting solution! (see Step 3 in the code)

### To run

    javac GarberBlockCalc.java
    java GarberBlockCalc 48 10