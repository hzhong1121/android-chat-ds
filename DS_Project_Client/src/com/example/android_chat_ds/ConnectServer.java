package com.example.android_chat_ds;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectServer implements Serializable{

	private static final long serialVersionUID = 603245052033569654L;

	public static final int port = 8988;
	private String host = "192.168.51.11";
	private InetAddress iaddr;

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public void connectServer() {
		try {
			iaddr = InetAddress.getByName(host);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}			
		socket = null;
		try {
			socket = new Socket(iaddr, port);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Return true if objects are identical.
	public boolean sendMessageToServer(Object sendMsg) {
		try {
			oos.writeObject(sendMsg);
			oos.flush();
		} 
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	 }	
	
	public Object receiveMessageFromServer() {
		Object receiveMsg = null;
		try {
			receiveMsg = ois.readObject();
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return receiveMsg;
	}
}