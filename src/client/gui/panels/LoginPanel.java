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


/**
* @desc        This is the login panel which allows the user to choose a username
*              and protocol method (UDP/TCP-IP)
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*/


public class LoginPanel{
	
	private JPanel wrapperPanel = new JPanel(new BorderLayout());
	
	/**
	* @constructor
	* creates the login panel - first panel you see upon opening the application
	*/
	public LoginPanel(){
	
	}//end constructor
	
	
	public JPanel getPanel(){
		
		
		return this.wrapperPanel;
	}
	
	
}//end class: LoginPanel