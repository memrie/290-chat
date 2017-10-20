
/////////////// Directory
package client.gui.panels;

/////////////// Event Libraries         
import java.awt.*;
import java.awt.event.*;

/////////////// General Java Libraries
import java.util.*;
import java.io.*;
import java.time.*;
import java.text.*;
import javax.swing.text.DefaultCaret;

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
	
	//wrapper panels
	private JPanel wrapper = new JPanel(new BorderLayout());
	private JPanel your_message_panel = new JPanel(new BorderLayout());
	private JPanel button_container = new JPanel(new BorderLayout());
	
	//message controls
	private JTextArea messagesList = new JTextArea();
	private JLabel yourMessageLbl =  new JLabel("Your Message");
	private JTextArea yourMessageTxt = new JTextArea(5,5);
	private JButton sendMessage = new JButton("send");
	
	//scroll wrapper panels
	private JScrollPane message_list_scroll = new JScrollPane(messagesList);
	private JScrollPane message_scroll = new JScrollPane(yourMessageTxt);
	
	/**
	* @constructor
	*/
	public ChatPanel(){
		//add some padding to overall panel
		this.wrapper.setBorder(new EmptyBorder(30, 30, 30, 30));
		
		//add some padding to wrapper panels so it's easier to see their controls
		this.yourMessageTxt.setBorder(new EmptyBorder(5, 5, 5, 5));
		button_container.setBorder(new EmptyBorder(10, 0, 5, 0));
		your_message_panel.setBorder(new EmptyBorder(5, 0, 5, 0));
		
		//don't let them edit the messageList
		this.messagesList.setEnabled(false);
		this.messagesList.setDisabledTextColor(Color.BLACK);
		this.messagesList.setLineWrap(true);
		DefaultCaret caret = (DefaultCaret) messagesList.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		//add button to it's container (border layout to take up width)
		button_container.add(sendMessage, BorderLayout.NORTH);
		
		//add your message controls to their wrapper
		your_message_panel.add(yourMessageLbl, BorderLayout.NORTH);
		your_message_panel.add(message_scroll, BorderLayout.CENTER);
		your_message_panel.add(button_container, BorderLayout.SOUTH);
		
		//add wrapper panels to main panel
		this.wrapper.add(message_list_scroll,BorderLayout.CENTER);
		this.wrapper.add(your_message_panel, BorderLayout.SOUTH);
	}//end constructor
	
	
	/**
	* Give the main panel for the chat screen
	* @return JPanel the main panel
	*/
	public JPanel getPanel(){
		return this.wrapper;
	}//end method: getPanel
	
	public JButton getSendButton(){
		return this.sendMessage;
	}
	
	/**
	* get the message the user wants to send
	* @return String the message they want to send
	*/
	public String getMessage(){
		return this.yourMessageTxt.getText();
	}//end method: getMessage
	
	/**
	* adds the new message to the list of messages
	* @param String the message to add
	*/
	public void updateMessagesList(String msg){
		this.messagesList.append("\n" + msg);
		this.yourMessageTxt.requestFocus();
	}//end method: updateMessagesList
	
	public void clearMessage(){
		this.yourMessageTxt.setText("");
	}
	
	
}//end class: ChatPanel