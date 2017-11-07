/////////////// Directory
package server.servers;

/**
* @desc        The main server class which any other protocol will be built from
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*/

public class Servers {

   public static void main(String[] args) {
       // Start TCP Server
       //TCPServer tcp = new TCPServer();
   
       // Start UDP Server -- UDP serve will only start if TCP is not started
       UDPServer udp = new UDPServer();
   }
}//end class: Server