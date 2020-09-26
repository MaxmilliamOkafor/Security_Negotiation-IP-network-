package server1;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
	String MyEncrypyionKey = "Hardcode key";
	String EncryptionKey = ("My strong encryption key");
    
    //Main Method:- called when running the class file.
    public static <XORencrypytion, XORencrypytionWrite> void main(String[] args){ 
        
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
                
                CaesarWrite CaesarWrite = new CaesarWrite();
                CaesarWrite.start();
             
                
       
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
                    //Reads and Splits by colon
                    String message = selfs.input.readUTF();
                    String[] userInputSplit = message.split(":");
                   
                    //checks which encryption type and decrypts it
                    switch(userInputSplit[0].toUpperCase()){
                    
                   
                    case "VIGENERE":
                    WriteMessageToFile("Vigenere Encrypted: " + userInputSplit[1]);
                    userInputSplit[1] = VigenereDecrypt(userInputSplit[1],"Hi");
                    WriteMessageToFile("Vigenere Decrypted: " + userInputSplit[1]);
                   
                    for (socketManager sm : clients) {
                    sm.output.writeUTF("Vigenere Unencrypted: " + userInputSplit[1]);
                    }
                    break;
                   
                    case "CAESAR":
                    WriteMessageToFile("Caesar Encrypted: " + userInputSplit[1]);
                    userInputSplit[1] = CaesarDecrypt(userInputSplit[1],4);
                    WriteMessageToFile("Caesar Decrypted: " + userInputSplit[1]);
                   
                    for (socketManager sm : clients) {
                    sm.output.writeUTF("Caesar Unencrypted: " + userInputSplit[1]);
                    }
                    break;
                    
                    case "XORENCRYPTION":
                        WriteMessageToFile("XORencryption Encrypted: " + userInputSplit[1]);
                        userInputSplit[1] = XORencryptDecrypt(userInputSplit[1]);
                        WriteMessageToFile("XORencryption Decrypted: " + userInputSplit[1]);
                       
                        for (socketManager sm : clients) {
                        sm.output.writeUTF("XORencryption Unencrypted: " + userInputSplit[1]);
                        }
                        break;
                   
                   
                    default:
                    for (socketManager sm : clients) {
                    sm.output.writeUTF("Please Enter Encryption and Message. eg (Caesar:Message)");
                    }
                    break;
                   
                    }
            }
          
        }
        catch (Exception except){
            //Exception thrown (except) when something went wrong, pushing message to the console
            System.out.println("Error in ServerHandler--> " + except.getMessage());}
        }
    
    
public void  Negotiation() {
}


public static void main(String[] args) {
	String encrypted = XORencryption.encryptDecrypt("I want cheese burger");
	System.out.println("Encrypted:" + encrypted);
	
	String decrypted = XORencryption.encryptDecrypt(encrypted);
	System.out.println("Decrypted:" + decrypted);
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
	
    static char[] chars = {
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
        'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
        'y', 'z', '0', '1', '2', '3', '4', '5',
        '6', '7', '8', '9', 'A', 'B', 'C', 'D',
        'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z', '!', '@',
        '#', '$', '%', '^', '&', '(', ')', '+',
        '-', '*', '/', '[', ']', '{', '}', '=',
        '<', '>', '?', '_', '"', '.', ',', ' '
    };
    

    // Caesar cipher
    static String encrypt(String text, int offset)
    {
        char[] plain = text.toCharArray();

        for (int i = 0; i < plain.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j <= chars.length - offset) {
                    if (plain[i] == chars[j]) {
                        plain[i] = chars[j + offset];
                        break;
                    }
                } 
                else if (plain[i] == chars[j]) {
                    plain[i] = chars[j - (chars.length - offset + 1)];
                }
            }
        }
        return String.valueOf(plain);
    }

    static String CaesarDecrypt(String cip, int offset)
    {
        char[] cipher = cip.toCharArray();
        for (int i = 0; i < cipher.length; i++) {
            for (int j = 0; j < chars.length; j++) {
                if (j >= offset && cipher[i] == chars[j]) {
                    cipher[i] = chars[j - offset];
                    break;
                }
                if (cipher[i] == chars[j] && j < offset) {
                    cipher[i] = chars[(chars.length - offset +1) + j];
                    break;
                }
            }
        }
        return String.valueOf(cipher);
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
    
    public static String XORencryptDecrypt(String input) {
		char[] key = {'K', 'C', 'Q'}; //Can be any chars, and any length array
		StringBuilder output = new StringBuilder();
		
		for(int i = 0; i < input.length(); i++) {
			output.append((char) (input.charAt(i) ^ key[i % key.length]));
		}
 		
		return output.toString();
	}
    
    public void WriteMessageToFile(String text) {
    	try { 
    		BufferedWriter writer = new BufferedWriter (new FileWriter ("Message.txt", true));
    		writer.write (text);
    		writer.newLine();
    		writer.close();
    	}
    	catch(IOException e){
    		e.printStackTrace();
    	}
    }
    
    
	
    void XORencryptionEncryptDecrypt (String[] args) {
		String encrypted = XORencryption.encryptDecrypt("I want cheese burger");
		System.out.println("Encrypted:" + encrypted);
		
		String decrypted = XORencryption.encryptDecrypt(encrypted);
		System.out.println("Decrypted:" + decrypted);
	}
}