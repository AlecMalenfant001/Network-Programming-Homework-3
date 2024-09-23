## Programming Assignment 3  
CS 33600  
Network Programming  
Spring, 2024

This assignment makes use of the files contained in this [zip file](http://math.pnw.edu/~rlkraft/cs33600/homework/hw3.zip). 

This assignment is based on the two client/server pairs,

-   AdditionClient\_v2/AdditionServer\_v2
-   AdditionClient\_v3/AdditionServer\_v3

from [client-server-experiments.zip](http://math.pnw.edu/~rlkraft/cs33600/for-class/client-server-experiments.zip). In this assignment you will write four client/server pairs

-   AdditionClient\_Hw3\_v1/AdditionServer\_Hw3\_v1
-   AdditionClient\_Hw3\_v2/AdditionServer\_Hw3\_v2
-   AdditionClient\_Hw3\_v3/AdditionServer\_Hw3\_v3
-   AdditionClient\_Hw3\_v4/AdditionServer\_Hw3\_v4

that implement four more addition protocols.

The addition servers in [client-server-experiments.zip](http://math.pnw.edu/~rlkraft/cs33600/for-class/client-server-experiments.zip) accept a varying number of sequences of integers to add together. Those servers use the end-of-file (eof) condition to know when there are no more sequences to add up. The addition servers in this assignment will use either a counter or a sentinel to know when there are no more sequences to add up.

The servers `AdditionClient_Hw3_v1.java` and `AdditionClient_Hw3_v2.java` should both use a counter to determine when there are no more sequences expected from the client. The server `AdditionClient_Hw3_v1.java` should use a counter to know when it has read the last integer in a sequence. The server `AdditionClient_Hw3_v2.java` should use a sentinel to know when it has read the last integer in a sequence.

The servers `AdditionClient_Hw3_v3.java` and `AdditionClient_Hw3_v4.java` should both use a sentinel to determine when there are no more sequences expected from the client. The server `AdditionClient_Hw3_v3.java` should use a counter to know when it has read the last integer in a sequence. The server `AdditionClient_Hw3_v4.java` should use a sentinel to know when it has read the last integer in a sequence.

In summary, the four client/servers pairs should implement the following patterns.

-   client/server v1 should use a sequence counter and integer counters
-   client/server v2 should use a sequence counter and integer sentinels
-   client/server v3 should use a sequence sentinel and integer counters
-   client/server v4 should use a sequence sentinel and integer sentinels

In all of the client server pairs, the sentinel value should be any negative integer.

Here is what three client requests look like for each of the four client/server pairs.

```
    3 4 1 2 3 4 5 -1 -2 -3 -4 -5 3 10 -11 12

    3 1 2 3 4 -1 1 2 3 4 5 -1 10 11 12 -1

    4 1 2 3 4 5 -1 -2 -3 -4 -5 3 10 -11 12 -1

    1 2 3 4 -1 1 2 3 4 5 -1 10 11 12 -1 -1
```

Make sure you understand why each line represents exactly three requests from a single client and make sure that you can determine what numbers are in each request.

In the zip file there are data files `data_v1`, `data_v2`, `data_v3`, and `data_v4` that provide test data for each client/server pair. To use the test data, run a server from a command-line console.

```
   &gt; java AdditionServer_Hw3_v1
```

Then run its companion server from another command-line console and redirect the appropriate data file into the client.

```
  &gt; java AdditionClient_Hw3_v1  &lt;  data_v1
```

The resulting output from the client should match (except for the pid number) `data_v1_client_results.txt` and the resulting output from the server should match (except for the pid and ip numbers) `data_v1_server_results.txt`.
