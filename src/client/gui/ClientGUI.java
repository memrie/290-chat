/////////////// Directory
package client.gui;

/////////////// project libraries
import client.gui.panels.ChatPanel;
import client.gui.panels.LoginPanel;

/////////////// General Java Libraries
import java.util.*;
import java.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
	
	DatagramSocket clientSocket;
   InetAddress IPAddress;
   byte[] sendData = new byte[1024];
   byte[] receiveData = new byte[1024];
	
	
	//IP RegEx from
	//https://www.mkyong.com/regular-expressions/how-to-validate-ip-address-with-regular-expression/
	private static final String IPADDRESS_PATTERN =
		"^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
		"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	
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
					sendMessage("not");
					
            }//end action performed
      });//end action listener
		
		loginPanel.setUpFirstLogin();
	}//end constructor
	
	
	/**
	*	attempts to connect the user to their given sever
	* @return Boolean whether or not we could connect
	*/
	public Boolean connectToSocket(){
		this.protocol_method = this.loginPanel.getMethod();
		
		String unm = this.loginPanel.getUsername();
		if(unm.equals("")){
			loginPanel.setError("Choose a valid Username.");
			return false;
		}//end if: did they give us a username?
		
		
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
				
				try{
					String ip = this.loginPanel.getIPAddress();
					int port = this.loginPanel.getPort();
					if(ip.equals("") || port < 49152 || port > 65535 || !validIP(ip)){
						loginPanel.setError("Choose a valid IP and port number.");
						return false;
					}//end if: did they give us anything?
				}catch(Exception e){
					loginPanel.setError("Choose a valid IP and port number.");
					return false;
				}//end try/catch: incase they leave a number null
				//assume TCP/IP - they are on their own now
				this.connectToTCP();
				break;
		}//end switch: which protocol?
		
		return true;
	}//end method: connectToSocket
	
	
	/**
	* returns if the IP address matches a pattern or not
	* @param String the ip address to test
	*/
	public boolean validIP(String ip){
		Pattern p = Pattern.compile(IPADDRESS_PATTERN);
		Matcher m = p.matcher(ip);
	  	return m.matches();
	}//end method: validIP
	
	
	
	
	
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
	
	/**
	* Allows the user to connect to the UDP server
	*/
	public void connectToUDP(){
		try{
			clientSocket = new DatagramSocket();
         clientSocket.setBroadcast(true);
			IPAddress = InetAddress.getByName("localhost");
		  	this.ip = InetAddress.getLocalHost();

			Runnable run = new Runnable() {
			   public void run() {
				   sendMessage("none");
					while (true){
						try{
							receiveData = new byte[1024];
							DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
					      	clientSocket.receive(receivePacket);
					      	String modifiedSentence = new String(trim(receivePacket.getData()));
							chatPanel.updateMessagesList(modifiedSentence);
							
						}catch(IOException ioe){
							System.out.println("Exception has occured");
							//loginPanel.setError("There was an error connecting.");
						}//end try/catch
					}//end while: true
					
			   }//end run()
			 };//end runnable
			 
			 //start the server thread
			 new Thread(run).start();

		}catch(UnknownHostException uhe){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}catch(IOException ie){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}catch(NullPointerException ne){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}catch(Exception e){
			chatPanel.updateMessagesList("The server has been disconnected - you can no longer chat.");
		}//end try/catch
	}//end method: connectUDP
	
	/**
	* Allows the user to connect to the TCP server
	*/
	public void connectToTCP(){
		try{
			socket = new Socket(loginPanel.getIPAddress(), loginPanel.getPort());
			br = new BufferedReader( new InputStreamReader( this.socket.getInputStream()));
			pw = new PrintWriter( new OutputStreamWriter( this.socket.getOutputStream()));
			
			Runnable run = new Runnable() {
		   public void run() {
		   		sendMessage("none");
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
		    }//end while: true
		 };//end runnable
		 
		 //start the server's thread
		 new Thread(run).start();
			
			
		}catch(UnknownHostException uhe){
			loginPanel.setError("Please check the provided host and port number.");
		}// end of the catch: UnknownHost
		catch(IOException ie){
			loginPanel.setError("There was an error which prevented you from connecting to the server.");
		}catch(NullPointerException ne){
			loginPanel.setError("Please check the provided host and port number.");
		}catch(Exception e){
			loginPanel.setError("There was an error which prevented you from connecting to the server.");
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
	public void sendMessage(String _msg){
		String msgToSend = "";

		DateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
		//get current date time with Date()
		Date date = new Date();

		if(_msg != "none"){
			msgToSend = this.chat_user  + " [" + dateFormat.format(date) + "] : " + this.chatPanel.getMessage();
		} else {
			msgToSend = "A client has connected!";
		}

		//send message
		switch(this.protocol_method){
			case "udp":
				try{
					//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			      
			      //String sentence = inFromUser.readLine();
			      sendData = msgToSend.getBytes();

			      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
			      clientSocket.send(sendPacket);					
				}catch(Exception ioe){
					this.chatPanel.updateMessagesList("Your message failed to send.");
				}//end try/catch
				break;
			default:
				try{
					pw.println(msgToSend);
					pw.flush();
				}catch(Exception e){
					this.chatPanel.updateMessagesList("Your message failed to send.");
				}//end try/catch
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