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
	
	ArrayList<DatagramSocket> users = new ArrayList<DatagramSocket>();
  
	public UDPServer(){
		this.start();
	}
	
	
	public void broadcast(DatagramPacket dp){
		try{
			for(DatagramSocket ds : users){
				ds.send(dp);
			}
		}catch(IOException ioe){
		
		}
	}
	
	public void addUer(DatagramSocket user){
		for(DatagramSocket ds : users){
			if(user != ds){
				users.add(user);
			}
		}
	}
	
	public void run(){
		System.out.println("Starting up the server...");
		try{
			this.ds = new DatagramSocket(4445);
			InetAddress group = InetAddress.getByName("230.0.0.1");

			System.out.println("Server Running");
			while(true){
			
				receiveData = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				ds.receive(receivePacket);
				
				String sentence = new String(trim(receivePacket.getData()));
				System.out.println(sentence);
				
				
				//InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();
				sendData = sentence.getBytes();
				sendPacket = new DatagramPacket(sendData, sendData.length, group, port);
				ds.send(sendPacket);
			}//end while: continue to listen/broadcast out
			
			
			
			
		}catch(SocketException se){
			System.out.println("se - Fatal Error - Server Kill.");
			ds.close();
		}catch(IOException ioe){
			System.out.println("ioe - Fatal Error - Server Kill.");
			ds.close();
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