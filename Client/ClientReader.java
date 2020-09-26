package client1;

import java.io.DataInputStream;
import java.net.Socket;

class ClientReader implements Runnable {
	Socket cwSocket = null;

	public ClientReader(Socket inputSoc) {
		cwSocket = inputSoc;
	}

	public void run() {
		try {
			// Create the outputstream to send data through
			DataInputStream dataOut = new DataInputStream(cwSocket.getInputStream());

			System.out.println("Client writer running");

			while (true) {

				// Write message to output stream and send through socket
				System.out.println(dataOut.readUTF());
			}
			// close the stream once we are done with it
		} catch (Exception except) {
			// Exception thrown (except) when something went wrong, pushing message to the
			// console
			System.out.println("Error in Writer--> " + except.getMessage());
		}
	}
}package client1;

import java.io.DataInputStream;
import java.net.Socket;

class ClientReader implements Runnable {
	Socket cwSocket = null;

	public ClientReader(Socket inputSoc) {
		cwSocket = inputSoc;
	}

	public void run() {
		try {
			// Create the outputstream to send data through
			DataInputStream dataOut = new DataInputStream(cwSocket.getInputStream());

			System.out.println("Client writer running");

			while (true) {

				// Write message to output stream and send through socket
				System.out.println(dataOut.readUTF());
			}
			// close the stream once we are done with it
		} catch (Exception except) {
			// Exception thrown (except) when something went wrong, pushing message to the
			// console
			System.out.println("Error in Writer--> " + except.getMessage());
		}
	}
}