/////////////// Directory
package gui.panels;


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

	private Font genericTxt = new Font("Arial", Font.PLAIN, 16);
	private Dimension lblUsernameSize = new Dimension(200,20);
	private Dimension lblLoginSize = new Dimension(100,20);
	
	private JPanel wrapper = new JPanel(new BorderLayout());
	private JPanel user_prompt = new JPanel(new BorderLayout());
	private JPanel connect_prompt = new JPanel(new BorderLayout());
	
	private JPanel username_panel = new JPanel(new FlowLayout());
	private JPanel connect_panel = new JPanel(new FlowLayout());
	private JPanel connect_option_panel = new JPanel(new BorderLayout());
	
	//flow layouts to make elements center and format/not take up entire window
	private JPanel btn_container = new JPanel(new FlowLayout());
	private JPanel user_msg_container = new JPanel(new FlowLayout());
	private JPanel method_msg_container = new JPanel(new FlowLayout());
	
	
	private JLabel CHOOSE_MSG = new JLabel("<html><div style='padding-top:30px;text-align:center;font-size:14pt;font-weight:bold;'>please choose a username for this session</div>");
	private JLabel USERNAME_LBL = new JLabel("username: ");
	private JTextField user_name = new JTextField(20);
	
	private final JLabel METHOD_MSG = new JLabel("<html><div style='padding-top:30px;text-align:center; font-size:14pt;font-weight:bold;'>method of connection</div>");
	private JRadioButton tcp_ip = new JRadioButton("TCP/IP");
	private JRadioButton udp = new JRadioButton("UDP");
	
	private ButtonGroup method_group = new ButtonGroup();
	
	
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
		
		//add method message to panel
		method_msg_container.add(METHOD_MSG);
		this.connect_option_panel.add(method_msg_container, BorderLayout.CENTER);
		this.connect_option_panel.add(connect_panel, BorderLayout.SOUTH);
		
		//add button to flow layout panel to prevent from taking up entire bottom area
		this.btn_container.add(connect_btn);
		
		//add two main connection method panels to their wrapper
		this.connect_prompt.add(connect_option_panel, BorderLayout.NORTH);
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
	
	public JButton getConnectButton(){
		return this.connect_btn;
	}
	
	
	public String getUsername(){
	
		return "";
	}//end method: getUsername
	
	public String getMethod(){
		
		return "";
	}//end method: getMethod
	
}//end class: LoginPanel