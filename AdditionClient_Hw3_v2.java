/*


*/

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This version of the client will,
 * 1) Send the server a positive integer indicating the
 * number of integer sequences that will follow.
 * 2) Send the server a sequence of positive integers.
 * 3) Send the server a negative integer to end the sequence.
 * 4) Receive back from the server the sum of the sequence.
 * 5) If not the last sequence, then go back to step 2.
 * 6) Close the connection to the server.
 */
public class AdditionClient_Hw3_v2 {
   public static final int SERVER_PORT = 5000; // Should be above 1023.

   public static void main(String[] args) {
      Socket socket = null;
      BufferedReader in = null;
      PrintWriter out = null;

      final String hostName;
      if (args.length > 0) {
         hostName = args[0];
      } else {
         hostName = "localhost";
      }

      final int portNumber;
      if (args.length > 1) {
         portNumber = Integer.parseInt(args[1]);
      } else {
         portNumber = SERVER_PORT;
      }

      // Get this client's process id number (PID). This helps
      // to identify the client in the server's transcrip.
      final ProcessHandle handle = ProcessHandle.current();
      final long pid = handle.pid();
      System.out.println("CLIENT: Process ID number (PID): " + pid);

      // Make a connection to the server
      try {
         System.out.println("CLIENT: connecting to server: " + hostName + " on port " + portNumber);
         socket = new Socket(InetAddress.getByName(hostName), portNumber);

         in = new BufferedReader(
               new InputStreamReader(
                     socket.getInputStream()));
         out = new PrintWriter(socket.getOutputStream());
      } catch (IOException e) {
         System.out.println("CLIENT: Cannot connect to server.");
         // System.out.println( e );
         e.printStackTrace();
         System.exit(-1);
      }

      // Implement the appropriate client/server protocol.
      // get The number of sequences from stdin and send it to the server
      final Scanner stdin = new Scanner(System.in);
      final int sequenceCount = stdin.nextInt();
      out.println(sequenceCount); // Send each int as a text string on its own line.
      out.flush(); // all the ints

      // send the rest of the ints
      for (int i = 0; i < sequenceCount; i++) {

         // Send the server a sequence of ints.
         int n;
         while (stdin.hasNextInt()
               && (n = stdin.nextInt()) >= 0) {
            out.println(n); // Send each int as a text string on its own line.
         }
         out.println(-1); // Send -1 to end the sequence of integer values.
         out.flush(); // all the ints

         // Receive the server's response.
         try {
            final String response = in.readLine();
            final int sum = Integer.parseInt(response.trim());
            System.out.println("CLIENT: Server response is: sum = " + sum);
         } catch (IOException e) {
            System.out.println("CLIENT: Cannot receive response from server.");
            System.out.println(e);
         }
      }

      // Disconnect from the server.
      try {
         socket.close();
         System.out.println("CLIENT: Closed socket");
      } catch (IOException e) {
         System.out.println("CLIENT: Cannot disconnect from server.");
         System.out.println(e);
      }

   }
}
