

//////////////// Directory
package server.servers;

/////////////// General Java Libraries
import java.util.*;
import java.text.*;
import java.util.Random;



/*******************************************************************************
* @desc        The main IMServer class which all others will inherit from
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*******************************************************************************/

public class IMServer{
	ArrayList<String> users_connected = new ArrayList<String>();
	
	/**
	* @default
	*/
	public IMServer(){
	
	}//end constructor
	
	/**
	* Clean up the requested username of any invalid characters or spaces
	* @param username String the username they want
	* @return String the username cleaned up
	*/
	public String cleanUsername(String username){
		String clean_username = username;//later change this to equal empty string
		
		//go through and clean the username
		//remove spaces
		//remove special characters
		
		return clean_username;
	}//end method: cleanUsername
	
	
	
	/**
	* Goes through all the users to make sure we don't have duplicate 
	* Usernames and will generate a random number to append the user
	* @param requested_username String the username the user has asked for
	* @return String the username you can use
	*/
	public String connectUser(String requested_username){
		String username = cleanUsername(requested_username);
		int user_amt = 0;
		
		for(String user : users_connected){
			if(user.matches(requested_username)){
				user_amt++;
			}//end if: do we have a username that matches it?
		}//end for: go through all connected users
		
		if(user_amt > 0){//we know that there is another user with this username (or similar)
			//generate a random number to append to this username
			Random rand = new Random();
			int new_num = rand.nextInt(50) + user_amt;//50 is the max
			if(user_amt > 50){
				new_num = new_num * 10 * (new_num%3);
			}//end if: do we have more than 50 users with this username?
			
			//append number to their requested username
			username += Integer.toString(new_num);
		}//end if: do we have at least 1 match?
		
		//add user to the array
		users_connected.add(username);
		
		return username;
	}//end method: connectUser
	
	
	/**
	* Goes through all the connected users to find the match
	* and remove it from the list of connected users.
	* @param username String
	* @return boolean true if they were removed
	*/
	public boolean disconnectUser(String username){
		for(int i = 0; i < users_connected.size(); i++){
			if(users_connected.get(i) == username){
				users_connected.remove(i);//remove user - we have a match
				return true;
			}//end if: do we have a username that matches it?
		}//end for: go through all the connected users
		return false;
	}//end method: disconnectUser
	
	
	
}//end class: IMServer