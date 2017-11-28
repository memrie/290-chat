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
  
	public UDPServer(){
		this.start();
	}
	
	public void run(){
		try{
			this.ds = new DatagramSocket(9876);
			
			
			while(true){
				receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				ds.receive(receivePacket);
				String sentence = new String(trim(receivePacket.getData()));
				
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				
				if(sentence == "Connecting..."){
					sentence = "Connected";
				}
				
				System.out.println(sentence);
				
				
				
				//String capitalizedSentence = sentence.toUpperCase();
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
				ds.send(sendPacket);
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