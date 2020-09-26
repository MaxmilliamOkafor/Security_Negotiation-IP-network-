package client1;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

import server1.socketManager;

public class Caesar {
    
    //Main Method:- called when running the class file.
    public static void main(String[] args){ 
        
        //Portnumber:- number of the port we wish to connect on.
        int portNumber = 15882;
        try{
            //Setup the socket for communication 
            ServerSocket serverSoc = new ServerSocket(portNumber);
            ArrayList<socketManager> clients = new ArrayList<socketManager>();
            
            while (true){
                
                //accept incoming communication
                System.out.println("Waiting for client");
                Socket soc = serverSoc.accept();
                socketManager temp = new socketManager(soc);
                clients.add(temp);
                //create a new thread for the connection and start it.
                ServerConnetionHandler sch = new ServerConnetionHandler(clients, temp);
                Thread schThread = new Thread(sch);
                schThread.start();
            }
            
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error --> " + except.getMessage());
        }
    }   
}
    
class ServerConnetionHandler implements Runnable
{
    socketManager selfs = null;
    ArrayList<socketManager> clients = null;
    
    public ServerConnetionHandler (ArrayList<socketManager> l, socketManager inSoc){
        selfs = inSoc;
        clients = l;
    }
    
    public void run(){
        try{
            //Catch the incoming data in a data stream, read a line and output it to the console
            
            System.out.println("Client Connected");
            while (true) {
            //Print out message
            	String message = selfs.input.readUTF();
            	System.out.println("--> " + message);
            	

	            	for (socketManager sm : clients) {
	            		sm.output.writeUTF(selfs.soc.getInetAddress().getHostAddress() + ":" + selfs.soc.getPort() + " wrote: " + message);
	            	}

            }
            //close the stream once we are done with it
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in ServerHandler--> " + except.getMessage());}
        }
public void  Negotiation() {
}

	String Input = new String("I said hi");
 
	public static String encrypt(String plainText, int shift){
		if(shift>26){
			shift = shift%26;
		}
		else if(shift<0) {
			shift = (shift%26)+26;
		}
		
		String cipherText = "";
		int length = plainText.length();
		for(int i = 0; i<length; i++){
			char ch = plainText.charAt(i);
			if(Character.isLetter(ch)){
				if(Character.isLowerCase(ch)){
					char c = (char)(ch+shift);
					if (c>'z'){
						cipherText += (char)(ch - (26-shift));
					}
					else {
						cipherText +=c;
					}
					
				}
				else if(Character.isUpperCase(ch)){char c = (char)(ch+shift);
				if (c>'Z'){
					cipherText += (char)(ch - (26-shift));
				}
				else {
					cipherText +=c;
				}
			    }
		   }
			else{
				 cipherText += ch;
			}
		}
	
		return cipherText;
}
}