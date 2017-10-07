/////////////// Directory
package server.servers;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
/**
* @desc        This is the server which uses the TCP/IP protocol specifically.
*
* @date        9/15/2017
* @author      Amber Normand, Erika Tobias, Kristen Merritt
*/


public class TCPServer implements Server{
    private ServerSocket ss;
    private InetAddress ip;
    private Vector<PrintWriter> pwList = new Vector<PrintWriter>();
    private boolean runServer;

    /** TCPServer Constructor
     *  Creates threads from inner class
     *  to handle multiple clients
     */
    public TCPServer(){
        try{
            ip = InetAddress.getLocalHost();
            System.out.println("Listening on IP: " + ip.getHostAddress());
            ss = new ServerSocket(0); // port 0 checks for open ports
            System.out.println("Listening on port: " + ss.getLocalPort());
            runServer = true;
            startServer();
        }
        catch(UnknownHostException uh){
            System.out.println("UnknownHostException | TCPServer constructor");
            System.out.println(uh.getMessage());
        }
        catch(Exception e){
            System.out.println("Exception | TCPServer constructor");
            System.out.println(e.getMessage());
        }
    }

    public void startServer(){
        try {
            while(runServer){
                System.out.println("Waiting for new client...");
                Socket cs = ss.accept();
                System.out.println("Found a client!");

                //new thread to handle the client
                TCPClientHandler ts = new TCPClientHandler(cs);
                ts.start();
            } // end loop
        }
        catch(IOException io){
            System.out.println("IOException | TCP | startServer");
            System.out.println(io.getMessage());
        }
    }

    public void stopServer(){
        runServer = false;
    }

    /** handles multiple clients */
    class TCPClientHandler extends Thread{
        private Socket cs;
        private PrintWriter pw;
        private BufferedReader br;

        /** ThreadedServer constructor */
        public TCPClientHandler(Socket cs){
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
                while(runServer){
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
}//end class: TCPServer