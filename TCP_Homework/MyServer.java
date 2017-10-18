/**
 *  @author Kristen Merritt, in close collaboration with Team 13
 *  4/7/15
 *  HW 07
 */
 
import java.io.*;
import java.net.*;
import java.util.*;

public class MyServer{
   private ServerSocket ss;
   private Vector<PrintWriter> pwList = new Vector<PrintWriter>();
   
   /** Creates a MyServer object */
   public static void main(String[]args){
      new MyServer();
   }//end of main
 
   /** MyServer Constructor 
    *  Creates threads from inner class
    *  to handle multiple clients
    */
   public MyServer(){
      try{
         ss = new ServerSocket(16789);
       
         while(true){
            System.out.println("Waiting for Client...");
            Socket cs = ss.accept();
            System.out.println("Found a Client!");
            
           //new thread to handle the client
            ThreadedServer ts = new ThreadedServer(cs);
            ts.start();  
         } // end loop
      } 
      catch(IOException ie){
         System.out.println("IOException | MyServer");
      }
   }// end MyServer constructor
 
/** handles multiple clients */
class ThreadedServer extends Thread{
   private Socket cs;
   private PrintWriter pw;
   private BufferedReader br;
   
   /** ThreadedServer constructor */
   public ThreadedServer(Socket cs){
      this.cs = cs;
   }// end constructor
   
   
   /** Creates print readers and writers
    *  Sends the welcome message
    *  Takes in the client msgs
    *  Sends back msgs to everyone
    */
   public void run(){    
      try{
         pw = new PrintWriter ( new OutputStreamWriter( cs.getOutputStream()));
         pw.println("Welcome \n");
         pw.flush();
         pwList.add( pw );
         br = new BufferedReader( new InputStreamReader( cs.getInputStream()));

        //String clientMsg;
         while(true){
            System.out.println("Waiting for msg...");
            String clientMsg = br.readLine();
            System.out.println(clientMsg);
            for(int i=0; i<pwList.size(); i++){
               pwList.get(i).println(clientMsg);
               pwList.get(i).flush();
            }
         }
      }
      catch(IOException ie){
         for(int i=0; i<pwList.size(); i++){
               pwList.get(i).println("A client has disconnected");
               pwList.get(i).flush();
            }
         System.out.println("IOException | run");     
      }          
   }// end of run method
}//end of ThreadedServer
}// end of MyServer