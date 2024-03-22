/*
      Course: CS 33600
      Name: Alec Malenfant
      Email: alecmalenf@pnw.edu
      Assignment: 3
   */

import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This version of the client will,
 * 1) Send the server a positive integer indicating the
 * number of integer sequences that will follow.
 * 2) Send the server a positive integer indicating
 * the length of a sequence of integer values.
 * 3) Send the server a sequence of integers with the specified length.
 * 4) Receive back from the server the sum of the sequence.
 * 5) If not the last sequence, then go back to step 2.
 * 6) Close the connection to the server.
 */
public class AdditionClient_Hw3_v1 {
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

      // 1) Send the server a positive integer indicating the
      //number of integer sequences that will follow.
      final Scanner stdin = new Scanner(System.in); 
      final int sequenceCount = stdin.nextInt(); //read the number of integer sequences
      out.println(sequenceCount); // Send each int as a text string on its own line.
      out.flush(); // all the ints

      // send the rest of the ints
      for (int i = 0; i < sequenceCount; i++) {

         // 2) Send the server a positive integer indicating
         // the length of a sequence of integer values.
         final int intCount = stdin.nextInt(); 
         out.println(intCount); // Send each int as a text string on its own line.
         out.flush(); // send the int

         // 3) Send the server a sequence of integers with the specified length.
         for (int j = 0; j < intCount; j++) {
            final int n = stdin.nextInt();
            out.println(n); // Send each int as a text string on its own line.
         }
         out.flush(); // send all the ints

         // 4) Receive back from the server the sum of the sequence.
         try {
            final String response = in.readLine();
            final int sum = Integer.parseInt(response.trim());
            System.out.println("CLIENT: Server response is: sum = " + sum);
         } catch (IOException e) {
            System.out.println("CLIENT: Cannot receive response from server.");
            System.out.println(e);
         }

         // 5) If not the last sequence, then go back to step 2.
      }

      // 6) Close the connection to the server.
      try {
         socket.close();
         System.out.println("CLIENT: Closed socket");
      } catch (IOException e) {
         System.out.println("CLIENT: Cannot disconnect from server.");
         System.out.println(e);
      }

   }
}
