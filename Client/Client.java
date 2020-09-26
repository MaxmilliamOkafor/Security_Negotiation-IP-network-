package client1;
import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.Scanner;

import server1.XORencryption;

public class Client {

    //Main Method:- called when running the class file.
    public static <CaesarWriter> void main(String[] args){ 
        
        //Portnumber:- number of the port we wish to connect on.
        int portNumber = 15882;
        //ServerIP:- IP address of the server.
        String serverIP = "localhost";
        
        try{
            //Create a new socket for communication
            Socket soc = new Socket(serverIP,portNumber);
            
         // create new instance of the client writer thread, intialise it and start it running
            ClientReader clientRead = new ClientReader(soc);
            Thread clientReadThread = new Thread(clientRead);
            clientReadThread.start();
            
            // create new instance of the client writer thread, intialise it and start it running
            ClientWriter clientWrite = new ClientWriter(soc);
            Thread clientWriteThread = new Thread(clientWrite);
            clientWriteThread.start();
         
            Caesar CaesarWrite = new Caesar();
            clientWrite.start();
            
            Vigenere VigenereWrite = new Vigenere();
            clientWrite.start();
         
            XORencryption XORencryptionWrite = new XORencryption();
            clientWrite.start();
            
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error --> " + except.getMessage());
        }
    }
}

//This thread is responsible for writing messages
class ClientWriter implements Runnable
{
	boolean running = true;
    Socket cwSocket = null;
    public ClientWriter (Socket outputSoc){
        cwSocket = outputSoc;
    }
    
    public void start() {
		// TODO Auto-generated method stub
		
	}
    
    
    
    public static String CaesarEncrypt(String plainText, int shift){
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
	
	public static String VigenereEncrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }
 
    public static String VigenereDecrypt(String text, final String key)
    {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++)
        {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z')
                continue;
            res += (char) ((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }
 
    {
        String key = "VIGENERECIPHER";
        String message = "I love chicken burger";
        String encryptedMsg = VigenereEncrypt(message, key);
        System.out.println("String: " + message);
        System.out.println("Encrypted message: " + encryptedMsg);
        System.out.println("Decrypted message: " + VigenereDecrypt(encryptedMsg, key));
    }
    
    private static String XORencryptDecrypt(String input) {
		char[] key = {'K', 'C', 'Q'}; //Can be any chars, and any length array
		StringBuilder output = new StringBuilder();
		
		for(int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key[i % key.length]));
		}
 		
		return output.toString();
	}

	public void run(){
    	Random rand = new Random();
    	int start = rand.nextInt(cwSocket.getLocalPort());
        try{
            //Create the outputstream to send data through
            DataOutputStream dataOut = new DataOutputStream(cwSocket.getOutputStream());
            
            
            while (running) {
            	
            	Scanner scan = new Scanner(System.in);
            	String userInput = scan.nextLine();
            	//Splits by Colon
            	String[] userInputSplit = userInput.split(":");
            	//Checks the encryption Type
            	switch(userInputSplit[0].toUpperCase()){
            	           
            	            case "VIGENERE":
            	            userInputSplit[1] = VigenereEncrypt(userInputSplit[1], "Hi");
            	            break;
            	           
            	            case "CAESAR":
            	            userInputSplit[1] = CaesarEncrypt(userInputSplit[1],4);
            	            break;
            	           
            	            
            	            case "XORENCRYPTION":
                	            userInputSplit[1] = XORencryptionEncrypt(userInputSplit[1].toUpperCase());
                	            break;
                	            
            	            default:
            	            System.out.println("Please Enter Encryption and Message. eg (XORencryption:Message)");
            	            break;
            	}
            	//Sends Encrypted Message to Server
            	dataOut.writeUTF(userInputSplit[0] + ":" + userInputSplit[1]);
            	userInput = scan.nextLine();
            	dataOut.flush();
            	}
            	dataOut.close();
            	}
        catch (IOException e) {
        	
        	e.printStackTrace();  

        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in Writer--> " + except.getMessage());
        }
    }
    
    private String XORencryptionEncrypt(String string) {
		// TODO Auto-generated method stub
    	//
    		char[] key = {'K', 'C', 'Q'}; //Can be any chars, and any length array
    		StringBuilder output = new StringBuilder();
    		
    		for(int i = 0; i < string.length(); i++) {
    			output.append((char) (string.charAt(i) ^ key[i % key.length]));
    		}
     		
    		return output.toString();
	}

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


//This thread is responsible for writing messages
class ClientReader implements Runnable
{
  Socket cwSocket = null;
  
  public ClientReader (Socket inputSoc){
      cwSocket = inputSoc;
  }

  public void run(){
      try{
          //Create the outputstream to send data through
          DataInputStream dataOut = new DataInputStream(cwSocket.getInputStream());
          
          System.out.println("Client writer running");
          
          while (true) {
        
          //Write message to output stream and send through socket
          System.out.println(dataOut.readUTF());
          }  
          //close the stream once we are done with it
      }
      catch (Exception except){
          //Exception thrown (except) when something went wrong, pushing message to the console
          System.out.println("Error in Writer--> " + except.getMessage());
      }
  }
}
}