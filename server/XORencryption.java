package server1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XORencryption {
	
	public static String encryptDecrypt(String input) {
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
	
	public static void main(String[] args) {
		String encrypted = XORencryption.encryptDecrypt("I want cheese burger");
		System.out.println("Encrypted:" + encrypted);
		
		String decrypted = XORencryption.encryptDecrypt(encrypted);
		System.out.println("Decrypted:" + decrypted);
	}
	void xorString(String data, String key) {
		if(data == null || key == null){
			return;
		}
	}

	public static void encryptDecrypt1(String input) {
		// TODO Auto-generated method stub
		return ;
	}
}