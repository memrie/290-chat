/////////////// Directory
package server.servers;

import java.util.*;

import java.io.*;
import java.net.*;

/**
* @desc        This server works specifically with the UDP Protocol
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*/


public class UDPServer extends Thread implements Server{
  
	DatagramSocket ds;
	DatagramPacket sendPacket;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
   InetAddress IPAddress;
   
   ArrayList<Integer> connected_ports = new ArrayList<Integer>();
  
	public UDPServer(){
      try{
         IPAddress = InetAddress.getLocalHost();
         System.out.println("Starting up server...");
         System.out.println("Listening on IP: " + IPAddress.getHostAddress());
      }catch(UnknownHostException uhe){
      
      }
		this.start();
	}//end constructor
   
   /**
   * Checks if the port is already in our ArrayList and if not, adds it
   * @param int the port we want to add
   */
   public void checkPort(int port){
      boolean match = false;
      for(Integer p : connected_ports){
         if(p == port){
            match = true;
            break;
         }//end if: do we already have this port?
      }//end for: go through all connected ports
      
      if(!match){
         connected_ports.add(port);
      }//end if: did we have a match?
   }//end method: checkPort
   
   /**
   * Sends the message out to all connected ports
   * @param byte[] the message we want to send
   * @param InetAddress the ip address we want to send to
   */
   public void broadcastMessage(byte[] msg, InetAddress ip){
      try{
         for(Integer p : connected_ports){
            sendPacket = new DatagramPacket(msg, msg.length, ip, p);
            this.ds.send(sendPacket);
         }//end for: go through all connected ports
      }catch(IOException ioe){
         System.out.println("IOE - something is wrong");
      }catch(Exception e){
         
      }//end try/catch
   }//end method: broadcastMessage
   
	
	public void run(){
      System.out.println("Running Server");
      
		try{
			this.ds = new DatagramSocket(9876);
         this.ds.setBroadcast(true);
			
			
			while(true){
				receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				ds.receive(receivePacket);
				String sentence = new String(trim(receivePacket.getData()));
				System.out.println(sentence);
				
				
				
				int port = receivePacket.getPort();
            this.checkPort(port);
				sendData = sentence.getBytes();
				this.broadcastMessage(sendData, IPAddress);
			}//end while: continue to listen/broadcast out
			
			
			
			
		}catch(SocketException se){
		
		}catch(IOException ioe){
		
		}
	
	
	}//end method: run
  
	public void startServer(){
		
	}
  
	public void stopServer(){
		System.out.println("Server has been killed. Sayonara!");
  		ds.close();
	}
  
  
  /**
	* Utility function from: https://goo.gl/GQ3fR7
	* 
	*/
	static byte[] trim(byte[] bytes){
		int i = bytes.length - 1;
		while (i >= 0 && bytes[i] == 0){
			--i;
		}

		return Arrays.copyOf(bytes, i + 1);
	}//end method: trim    
}//end class: UDPServer