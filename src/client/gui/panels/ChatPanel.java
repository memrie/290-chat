

package gui.panels;

/////////////// Event Libraries         
import java.awt.*;
import java.awt.event.*;

/////////////// 
import java.util.*;
import java.time.*;
import java.text.*;

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
* @desc        This is the chat panel which allows the user to send and 
* 					recieve and view messages to/from other users.
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*******************************************************************************/

public class ChatPanel{
	
	private JPanel wrapper = new JPanel(new BorderLayout());
	private JPanel your_message_panel = new JPanel(new BorderLayout());
	private JPanel button_container = new JPanel(new FlowLayout());
	
	
	
	
	private JTextArea messagesList = new JTextArea();
	private JLabel yourMessageLbl =  new JLabel("Your Message");
	private JTextArea yourMessageTxt = new JTextArea();
	private JButton sendMessage = new JButton("send");
	
	private JScrollPane message_list_scroll = new JScrollPane(messagesList);
	private JScrollPane message_scroll = new JScrollPane(yourMessageTxt);
	
	
	public ChatPanel(){
		this.wrapper.setBorder(new EmptyBorder(30, 30, 30, 30));
		
		button_container.add(sendMessage);
		this.messagesList.setEnabled(false);
		this.yourMessageTxt.setBorder(new EmptyBorder(5, 5, 5, 5));
			
		your_message_panel.add(yourMessageLbl, BorderLayout.NORTH);
		your_message_panel.add(message_scroll, BorderLayout.CENTER);
		your_message_panel.add(button_container, BorderLayout.SOUTH);
		
		
		this.wrapper.add(message_list_scroll,BorderLayout.CENTER);
		this.wrapper.add(your_message_panel, BorderLayout.SOUTH);
	}//end constructor
	
	
	
	public JPanel getPanel(){
		return this.wrapper;
	}//get method: getPanel
	
	
	/**
	* get the message the user wants to send
	*/
	public String getMessage(){
	
		return "";
	}//end method: getMessage
	
	
	
}//end class: ChatPanel