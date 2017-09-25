

/////////////// directory
package client.gui;

/////////////// Event Libraries         
import java.awt.*;
import java.awt.event.*;

////////////// GUI Libraries
import javax.swing.*; 
import javax.swing.plaf.metal.*;
import javax.swing.UIManager;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

/*******************************************************************************
* @desc        The menu bar for the chat application
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*******************************************************************************/

/*******************************************************************************
* TABLE OF CONTENTS
********************************************************************************
* FILE DROP DOWN 
* HELP DROP DOWN
* GETTERS --> MenuBar
********************************************************************************/

public class MenuBar{
	
	private JMenuBar jmb = new JMenuBar();
	private final String ABOUT_MSG = "NSSA 290 Chat Program UDP/TCP-IP \n Amber Normand, Kristen Merritt, Erika Tobais";
	///////////////////////////////// FILE DROP DOWN /////////////////////////////////
	private JMenu file = new JMenu("File");
	private JMenuItem exits = new JMenuItem("Exit");
	
	///////////////////////////////// HELP DROP DOWN /////////////////////////////////
	private JMenu help = new JMenu("Help");
	private JMenuItem about = new JMenuItem("about");

	/**
	* @constructor
	* @default
	*/
	public MenuBar(){		
		/////////////////////// Add item & Action to file menu
		this.file.add(exits);
		//close on "exit"
		this.exits.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               System.exit(1);
            }//end action performed
       });//end action listener
		
   		
		/////////////////////// Add item & Action to help menu
		this.help.add(about);
		//show about dialog
      this.about.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent e){
               JOptionPane.showMessageDialog(null, ABOUT_MSG, "About", JOptionPane.INFORMATION_MESSAGE);
            }//end action performed
      });//end action listener
   		
		/////////////////////// Add menus to menu bar
		this.jmb.add(file);
		this.jmb.add(help);
	}//end constructor
	
	
	/** ---------------------------------------------------------------------- **/
	/** -------------------------- GETTERS --> MenuBar ----------------------- **/
	/** ---------------------------------------------------------------------- **/
	
   /**
   *Gets the menu bar to support items
   *@return JMenu the menu bar
   */
	public JMenuBar getMenubar(){
		return this.jmb;
	}//end method: getMenubar
		
		
}//end class MenuBar