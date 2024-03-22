/*


*/

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This version of the client will,
 * 1) Either send the server a negative integer and go to step 6,
 * or go to step 2.
 * 2) Send the server a positive integer indicating
 * the length of a sequence of integer values.
 * 3) Send the server a sequence of integers with the specified length.
 * 4) Receive back from the server the sum of the sequence.
 * 5) Go to step 1.
 * 6) Close the connection to the server.
 */
public class AdditionClient_Hw3_v3 {
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

      /*
       * Note : There are -1's in the data that need to be added. Therefore, we should
       * check for the sentinal when
       * getting the number of integers that are going to be sent to the server.
       */

      // get the number of integers to send to the server from the first sequence of
      // ints
      final Scanner stdin = new Scanner(System.in);
      int intCount = stdin.nextInt();

      while (intCount >= 0) { // an integer value of -1 ends the sequence
         out.println(intCount); // Send each int as a text string on its own line.
         out.flush(); // send the int
         intCount = -1;

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
