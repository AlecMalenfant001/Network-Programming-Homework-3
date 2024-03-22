/*


*/

import java.net.*;
import java.io.*;

/**
 * For each client, this server will,
 * 1) Read an integer.
 * If the integer is negative,
 * then close the connection to the client,
 * otherwise read the specified number of integer values,
 * one value per line of text.
 * 2) Send back the sum of the sequence as a text string.
 * 3) Go back to step 1.
 */
public class AdditionServer_Hw3_v3 {
   public static final int SERVER_PORT = 5000; // Should be above 1023.

   public static void main(String[] args) {
      final int portNumber;
      if (args.length > 0) {
         portNumber = Integer.parseInt(args[0]);
      } else {
         portNumber = SERVER_PORT;
      }

      int clientCounter = 0;

      // Get this server's process id number (PID). This helps
      // to identify the server in TaskManager or TCPView.
      final ProcessHandle handle = ProcessHandle.current();
      final long pid = handle.pid();
      System.out.println("SERVER: Process ID number (PID): " + pid);

      // Get the name and IP address of the local host and
      // print them on the console for information purposes.
      try {
         final InetAddress address = InetAddress.getLocalHost();
         System.out.println("SERVER Hostname: " + address.getCanonicalHostName());
         System.out.println("SERVER IP address: " + address.getHostAddress());
         System.out.println("SERVER Using port no. " + portNumber);
      } catch (UnknownHostException e) {
         System.out.println("Unable to determine this host's address.");
         System.out.println(e);
      }

      // Create the server's listening socket.
      ServerSocket serverSocket = null;
      try {
         serverSocket = new ServerSocket(portNumber);
         System.out.println("SERVER online:");
      } catch (IOException e) {
         System.out.println("SERVER: Error creating network connection.");
         // System.out.println( e );
         e.printStackTrace();
         System.exit(-1);
      }

      while (true) // Serve multiple clients.
      { // Each client can make one request.
         Socket socket = null;
         BufferedReader in = null;
         PrintWriter out = null;

         // Wait for an incoming client request.
         try {
            socket = serverSocket.accept();

            // At this point, a client connection has been made.
            in = new BufferedReader(
                  new InputStreamReader(
                        socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());
         } catch (IOException e) {
            System.out.println("SERVER: Error connecting to client");
            System.out.println(e);
         }

         ++clientCounter;
         // Get the IP address of the client and log it to the console.
         final InetAddress clientIP = socket.getInetAddress();
         System.out.println("SERVER: Client " + clientCounter + ": IP: " + clientIP.getHostAddress());

         // Implement the appropriate client/server protocol.

         // Read the client's sequence of integer values,
         // sum them, and send back the sum.
         try {
            int sum = 0;
            String request;

            // read the first line to get the number of sequences to read from the client
            request = in.readLine();
            final int sequenceCount = Integer.parseInt(request);
            sum = sequenceCount;

            // send sum to client
            System.out.println("SERVER: Client " + clientCounter + ": Message received: sum = " + sum);
            out.println(sum); // Send the sum as a text string.
            out.flush(); // Now make sure that the response is sent.
            sum = 0;

            socket.close(); // We are done with the client's request.
            System.out.println("SERVER: Client " + clientCounter + ": Closed socket.");
         } catch (IOException e) {
            System.out.println("SERVER: Error communicating with client (Client no. " + clientCounter + ")");
            System.out.println(e);
         }

      }
   }
}
