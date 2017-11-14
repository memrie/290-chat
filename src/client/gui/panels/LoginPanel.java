/////////////// Directory
package client.gui.panels;


/////////////// Event Libraries         
import java.awt.*;
import java.awt.event.*;

/////////////// General Java Libraries
import java.util.*;
import java.io.*;

////////////// GUI Libraries
import javax.swing.*; 
import javax.swing.plaf.metal.*;
import javax.swing.border.*;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;


/*******************************************************************************
* @desc        This is the login panel which allows the user to choose a username
*              and protocol method (UDP/TCP-IP)
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*******************************************************************************/


public class LoginPanel{
	//protocol? tcp is default
	private String selected_method = "tcp";
	
	//the wrapper panels
	private JPanel wrapper = new JPanel(new BorderLayout());
	private JPanel user_prompt = new JPanel(new BorderLayout());
	private JPanel connect_prompt = new JPanel(new BorderLayout());
	
	//wrapper panels for the controls (connection method, username
	private JPanel username_panel = new JPanel(new FlowLayout());
	private JPanel connect_panel = new JPanel(new FlowLayout());
	private JPanel connect_option_panel = new JPanel(new BorderLayout());
	private JPanel connect_option_tcp_panel = new JPanel(new BorderLayout());
	
	//flow layouts to make elements center and format/not take up entire window
	private JPanel btn_container = new JPanel(new FlowLayout());
	private JPanel user_msg_container = new JPanel(new FlowLayout());
	private JPanel method_msg_container = new JPanel(new FlowLayout());
	
	//username messages/inputs
	private final JLabel CHOOSE_MSG = new JLabel("<html><div style='padding-top:30px;text-align:center;font-size:14pt;font-weight:bold;'>please choose a username for this session</div>");
	private final JLabel USERNAME_LBL = new JLabel("username: ");
	private JTextField user_name = new JTextField(20);
	
	//connection method messages/inputs
	private final JLabel METHOD_MSG = new JLabel("<html><div style='padding-top:30px;text-align:center; font-size:14pt;font-weight:bold;'>method of connection</div>");
	private JRadioButton tcp_ip = new JRadioButton("TCP/IP");
	private JRadioButton udp = new JRadioButton("UDP");
	
	//button group for the radio buttons
	private ButtonGroup method_group = new ButtonGroup();
	
	//IP and Port
	private JPanel tcp_ip_port_panel = new JPanel(new FlowLayout());
	private final JLabel ip_lbl = new JLabel("ip address: ");
	private JTextField ip_txt = new JTextField(10);
	
	private final JLabel port_lbl = new JLabel("port: ");
	private JTextField port_txt = new JTextField(5);
	
	//button for the user to actually connect to a server
	private JButton connect_btn = new JButton("connect");
	
	/**
	* @constructor
	* creates the login panel - first panel you see upon opening the application
	*/
	public LoginPanel(){
		this.wrapper.setBorder(new EmptyBorder(30, 30, 30, 30));
		//add buttons to method radio group
		this.method_group.add(tcp_ip);
		this.method_group.add(udp);
		
		
		//to avoid looping later, just update our attribute when the user clicks it
		tcp_ip.addActionListener(//update to TCP/IP
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               selected_method = "tcp";
					tcp_ip_port_panel.setVisible(true);
            }//end action performed
       });//end action listener
		 
		 udp.addActionListener(//update to UDP
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               selected_method = "udp";
					//tcp_ip_port_panel.setVisible(false);
            }//end action performed
       });//end action listener
		
		
		//username: textfield add
		this.username_panel.add(USERNAME_LBL);
		this.username_panel.add(user_name);
		
		//add username mesage and text/label
		user_msg_container.add(CHOOSE_MSG);
		this.user_prompt.add(user_msg_container, BorderLayout.NORTH);
		this.user_prompt.add(username_panel, BorderLayout.CENTER);
		user_prompt.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//method connection - radio buttons add to panel
		this.connect_panel.add(tcp_ip);
		this.connect_panel.add(udp);
		connect_panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//tcp/ip - obtain IP/port information
		this.tcp_ip_port_panel.add(this.ip_lbl);
		this.tcp_ip_port_panel.add(this.ip_txt);
		
		this.tcp_ip_port_panel.add(this.port_lbl);
		this.tcp_ip_port_panel.add(this.port_txt);
		
		this.connect_option_tcp_panel.add(this.connect_option_panel, BorderLayout.NORTH);
		this.connect_option_tcp_panel.add(this.tcp_ip_port_panel, BorderLayout.CENTER);
		this.tcp_ip_port_panel.setVisible(false);
		
		//add method message to panel
		method_msg_container.add(METHOD_MSG);
		this.connect_option_panel.add(method_msg_container, BorderLayout.CENTER);
		this.connect_option_panel.add(connect_panel, BorderLayout.SOUTH);
		
		//add button to flow layout panel to prevent from taking up entire bottom area
		this.btn_container.add(connect_btn);
		
		//add two main connection method panels to their wrapper
		this.connect_prompt.add(connect_option_tcp_panel, BorderLayout.NORTH);
		this.connect_prompt.add(btn_container,BorderLayout.CENTER);

		
		//add both prompts to main login panel wrapper
		this.wrapper.add(user_prompt,BorderLayout.NORTH);
		this.wrapper.add(connect_prompt,BorderLayout.CENTER);
		
	}//end constructor
	
	
	/** 
	* Gives you the login panel
	* @return JPanel the login panel
	*/
	public JPanel getPanel(){
		return this.wrapper;
	}//end functoin: getPanel
	
	/**
	* Gives you the connect button
	* @return JButton the connect button
	*/
	public JButton getConnectButton(){
		return this.connect_btn;
	}//end method: getConnectButton
	
	/**
	* gives you the username they entered
	* @return String the username they entered
	*/
	public String getUsername(){
		return this.user_name.getText();
	}//end method: getUsername
	
	/**
	* Gives you which server connection protocol they chose
	* @return String the protocol chosen
	*/
	public String getMethod(){
		return this.selected_method;
	}//end method: getMethod
	
	/**
	* Gives you the IP Address the user wants to connect to
	* @return String the IP Address 
	*/
	public String getIPAddress(){
		String ip_address = null;
		
		if(selected_method == "tcp"){
			ip_address = this.ip_txt.getText();
		}//end if: is this tcp/ip?
		
		return ip_address;
	}//end method: getIPAddress
	
	/**
	* Gives you which port the user wants to listen on
	* @return String the port 
	*/
	public int getPort(){
		int port_num = 0;//they can't have a port, random one
		if(selected_method == "tcp"){
			port_num = Integer.parseInt(this.port_txt.getText());
		}//end if: is this tcp/ip?
		return port_num;
	}//end method: getIPAddress
	
		
}//end class: LoginPanel