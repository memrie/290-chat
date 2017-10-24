/////////////// Directory
package server.servers;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
* @desc        This server works specifically with the UDP Protocol
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*/


public class UDPServer implements Server{
   private DatagramSocket sock = null;
   private InetAddress address;
   private boolean runServer;

   public UDPServer()
   {
       
      try
      {
         // Step 1 : Create a socket to listen at port 1234
         DatagramSocket ds = new DatagramSocket();
         byte[] receive = new byte[65535];
         
         DatagramPacket DpReceive = null;
         runServer = true;
         startServer();
         
         while (runServer)
         {
         
            // Step 2 : create a DatgramPacket to receive the data.
            DpReceive = new DatagramPacket(receive, receive.length);
         
            // Step 3 : revieve the data in byte buffer.
            ds.receive(DpReceive);
         
            System.out.println("Client:-" + data(receive));
         
            // Exit the server if the client sends "bye"
            if (data(receive).toString().equals("bye"))
            {
               System.out.println("Client sent bye.....EXITING");
               stopServer();
            }
         
            // Clear the buffer after every message.
            receive = new byte[65535];
         }
      }
      catch(IOException e)
      {
         System.err.println("IOException " + e);
      }
   }
   
   public void startServer(){
      
      try
      {
         Scanner sc = new Scanner(System.in);
      
        // Step 1:Create the socket object for
        // carrying the data.
         DatagramSocket ds = new DatagramSocket();
      
         InetAddress ip = InetAddress.getLocalHost();
         byte buf[] = null;
      
        // loop while user not enters "bye"
         while (runServer)
         {
            String inp = sc.nextLine();
         
            // convert the String input into the byte array.
            buf = inp.getBytes();
         
            // Step 2 : Create the datagramPacket for sending
            // the data.
            DatagramPacket DpSend =
                  new DatagramPacket(buf, buf.length, ip, 1234);
         
            // Step 3 : invoke the send call to actually send
            // the data.
            ds.send(DpSend);
         
            // break the loop if user enters "bye"
            if (inp.equals("bye"))
               stopServer();
         }}
      catch(IOException e)
      {
         System.err.println("IOException " + e);
      }
   }//end of start server
   
      
    public void stopServer(){
        runServer = false;
    }

  // A utility method to convert the byte array
    // data into a string representation.
   public static StringBuilder data(byte[] a)
   {
      if (a == null)
         return null;
      StringBuilder ret = new StringBuilder();
      int i = 0;
      while (a[i] != 0)
      {
         ret.append((char) a[i]);
         i++;
      }
      return ret;
   }
       
}//end class: UDPServer