/////////////// Directory
package client.gui;

/////////////// project libraries
import client.gui.panels.ChatPanel;
import client.gui.panels.LoginPanel;

/////////////// General Java Libraries
import java.util.*;
import java.text.*;

/////////////// Socket Handling Libraries (TCP/IP)
import java.net.ServerSocket;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.*;


/////////////// UDP Handling Libraries
import java.net.DatagramPacket;
import java.net.DatagramSocket;
//import java.util.Scanner;

////////////// GUI Libraries
import javax.swing.*;


/////////////// Event Libraries
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*******************************************************************************
* @desc        The main gui interface for the chat client
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*******************************************************************************/



public class ClientGUI extends JFrame{
	private String chat_user = "";
	private String protocol_method;
	
	private JFrame jf = new JFrame("Chat Client - NSSA 290"); //main frame
   
	//all the panels/elements needed for this application
   private MenuBar menuBar;
   private LoginPanel loginPanel;
	private ChatPanel chatPanel;
	
	//TCP/IP Specific
	private Socket socket;
	private BufferedReader br;
	private PrintWriter pw;
	
	//UDP Specific
	private int port;
	private Scanner sc;
   private DatagramSocket ds = null;
   private InetAddress ip;
   byte[] buf = null;
	DatagramPacket packet;
	
	
	/**
	* @CONSTRUCTOR
	*/
	public ClientGUI(){
		//////////////////// set some preferences on the GUI
      this.jf.setSize(600,500);
      this.jf.setLayout(new BorderLayout());
      this.jf.setLocationRelativeTo(null);
      this.jf.setResizable(false);
		
		
		//////////////////// Needed Elements
      this.menuBar = new MenuBar();
   	//////////////////// Initialize all Panels
      this.loginPanel = new LoginPanel();
		this.chatPanel = new ChatPanel();
	
	  	//////////////////// Lets get the program up for the first time
      jf.add(BorderLayout.PAGE_START, this.menuBar.getMenubar());//menu bar
      jf.add(this.loginPanel.getPanel());//login panel
   	
   	
   	
      //////////////////// Show it to the user
      jf.setVisible(true); // display jf
      jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //close by default
		
		//////////// Attach the conect event to the button
		loginPanel.getConnectButton().addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               
					if(connectToSocket()){
						//we're good - unload login panel, show chat
						unloadLoginPanel();
					}else{
						//um... that ain't right. show an error message
					}//end else/if: could we connect to the chosen server?
					
            }//end action performed
      });//end action listener
		
		
		////////// Attach the send message event to the button
		chatPanel.getSendButton().addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
					sendMessage();
					
            }//end action performed
      });//end action listener
		
	
	}//end constructor
	
	
	/**
	*	attempts to connect the user to their given sever
	* @return Boolean whether or not we could connect
	*/
	public Boolean connectToSocket(){
		this.protocol_method = this.loginPanel.getMethod();
		
		//based on connection, the server should make sure only 1
		//user has their username, otherwise prepend a numeric number
		//to diferentate the users. Server should return that username 
		//back to me, the client to update that username
		switch(protocol_method){
			case "udp" : 
				//connect to UDP
				this.connectToUDP();
				break;
			default :
				//assume TCP/IP
				this.connectToTCP();
				break;
		}//end switch: which protocol?
		
		return true;
	}//end method: connectToSocket
	
	
	public void connectToUDP(){
		System.out.println("in connectUDP");
		try{
			sc = new Scanner(System.in);
		   ds = new DatagramSocket();
		  	this.ip = InetAddress.getLocalHost();
			byte[] receiveData = new byte[1024];
			System.out.println(ip);
		   
			
			Runnable run = new Runnable() {
			   public void run() {
					while (true){
						//DatagramPacket packet = new DatagramPacket(buf, bug.length, ip, 4444);
						
						try{
							 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
						      ds.receive(receivePacket);
						      String modifiedSentence = new String(receivePacket.getData());
						      System.out.println("FROM SERVER:" + modifiedSentence);
						}catch(IOException ioe){
							System.out.println("fuckyou");
						}
					}
					
			   }
			 };
			 new Thread(run).start();

		}catch(UnknownHostException uhe){
			System.out.println("can't find ya...");
		}catch(IOException ie){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}catch(NullPointerException ne){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}catch(Exception e){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}//end try/catch
	
	}//end method: connectUDP
	
	
	public void connectToTCP(){
		try{
			socket = new Socket(loginPanel.getIPAddress(), loginPanel.getPort());
			br = new BufferedReader( new InputStreamReader( this.socket.getInputStream()));
			pw = new PrintWriter( new OutputStreamWriter( this.socket.getOutputStream()));
			
			Runnable run = new Runnable() {
		   public void run() {
				try{
					while(true){
						String line = br.readLine();
						chatPanel.updateMessagesList(line);
					}//end while
				}catch(IOException ie){
					chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
				}catch(NullPointerException ne){
					chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
				}catch(Exception e){
					chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
				}//end try/catch
		    }
		 };
		 new Thread(run).start();
			
			
		}catch(UnknownHostException uhe){
			//jtaMain.append("There is no server available");
			System.out.println("UnknownHostException | SendHandler");
		}// end of the catch: UnknownHost
		catch(IOException ie){
			System.out.println("IOException --> on connection");
		}catch(NullPointerException ne){
			System.out.println("Null pointer - couldn't connect");
		}catch(Exception e){
			System.out.println("General Exception - issue");
		}//end of IO catch

	}//end method: connectToTCP

	
	/**
	* removes the login panel and shows the chat panel to the user
	*/
	public void unloadLoginPanel(){
		this.chat_user = this.loginPanel.getUsername();
	
		//remove the initial panel
		this.jf.remove(this.loginPanel.getPanel());
		
		//load up the chat panel
		this.jf.add(this.chatPanel.getPanel());
		
		//redraw the panel so it updates
		this.jf.revalidate();
      this.jf.repaint();
      pack();
	}//end method: unloadLoginPanel
	
	
	
	
	/**
	* make a call to the server via the open socket
	*/
	public void sendMessage(){
		DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
		//get current date time with Date()
		Date date = new Date();
				
		//build out the message, with the username this user has chosen
		String msg = this.chat_user  + " [" + dateFormat.format(date) + "] : " + this.chatPanel.getMessage();
		//send message
		
		switch(this.protocol_method){
			case "udp":
				try{
					buf = msg.getBytes();
			      DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, ip,1222);
			      ds.send(sendPacket);
			
					
					System.out.println(buf);
					System.out.println(ip);
					System.out.println(buf.length);
					
				}catch(Exception ioe){
					System.out.println("There was an issue with UDP...");
					ioe.printStackTrace();
					
				}//end try/catch
				break;
			default:
				pw.println(msg);
				pw.flush();
				break;
		}//end switch: which protocol method?
		
		this.chatPanel.clearMessage();
	}//end method: sendMessage
	
	
	/**
	* add the message to the chat panel, it was recieved
	*/
	public void addMessage(String msg){
		//we need to base this on the socket events, method will be changed later.
		this.chatPanel.updateMessagesList(msg);
	}//end method: addMessage

	
}//end class: ClientGUI
