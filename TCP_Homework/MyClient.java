/**
 *  @author Kristen Merritt, in close collaboration with Team 13
 *  4/7/15
 *  HW 07
 */
 
 import java.net.*;
 import java.io.*;
 import java.awt.*;
 import java.awt.event.*;
 import javax.swing.*;


/** The client */
 public class MyClient extends JFrame{
   private JTextArea jtaMain;
   private JButton jbSend;
   private JTextField jtfMessage;
   private JPanel jpTop, jpBottom;
   private String ip;
   
   /** Creates the gui and its settings */
   public static void main(String [] args){
      MyClient gui = new MyClient(args);
      gui.setVisible(true);
      gui.setTitle("Let's Chat!");
      gui.setSize(500, 500);
      gui.setLocationRelativeTo(null);
      gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
   } // end main
 
   /** Sets up the gui */
   public MyClient(String [] args){
      try{
         if(args.length > 0){
            ip = args[0];
         }
         else{
            ip = InetAddress.getLocalHost().getHostAddress();
         }
      }
      catch(UnknownHostException uhe){
         System.out.println("Unknown Host");
      }
      jpTop = new JPanel();
      jtaMain = new JTextArea(25,35);
         jtaMain.setLineWrap( true );
         jtaMain.setWrapStyleWord( true );
         jtaMain.setEditable( false );
      JScrollPane mainPane = new JScrollPane(jtaMain);
      jpTop.add(mainPane);
      add(jpTop, BorderLayout.CENTER);
     
      jpBottom = new JPanel();
      jtfMessage = new JTextField("Send a Message");
         jtfMessage.setColumns(30);
         jtfMessage.requestFocus();
      jbSend = new JButton("Send");
      jpBottom.add(jtfMessage);
      jpBottom.add(jbSend);
      add(jpBottom, BorderLayout.SOUTH);
    
      //add items to SendHandler
      SendHandler sh = new SendHandler(jtaMain, jtfMessage);
      new Thread(sh).start();
      jbSend.addActionListener(sh);
   } // end constructor
 
   /** Create a class SendHandler */
   class SendHandler implements ActionListener, Runnable{
      private JTextArea jtaMain;
      private JTextField jtfMessage;
      private Socket cs;
      private BufferedReader br;
      private PrintWriter pw;
    
      /** SendHandler Constructor */
      public SendHandler(JTextArea jtaMain, JTextField jtfMessage){
         this.jtaMain = jtaMain;
         this.jtfMessage = jtfMessage;
         
         try{
            cs = new Socket(ip, 16789);
            br = new BufferedReader( new InputStreamReader( cs.getInputStream()));
            pw = new PrintWriter( new OutputStreamWriter( cs.getOutputStream()));
         }
         catch(UnknownHostException uhe){
            jtaMain.append("There is no server available");
            System.out.println("UnknownHostException | SendHandler");
         }// end of the catch
         catch(IOException ie){
            System.out.println("IOException | SendHandler");
         }//end of IO catch
      }//end of constructor
    
      public void actionPerformed(ActionEvent ae){
         //Sending jtaMessage to server
         String message = jtfMessage.getText();
         pw.println(message);
         pw.flush();
      }//end of ActionPerformed
      
      public void run(){
         try{
            while(true){
              String line = br.readLine();
              jtaMain.append(line + "\n");
            }
         }
         catch(IOException ie){
            jtaMain.append("The server has been disconnected - you can no longer chat.");
            System.out.println("IOException | actionperformed");
         }   
      } // end run
   }//end of SendHandler
 } // end MyClient