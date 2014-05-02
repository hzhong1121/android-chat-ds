package ds.project.server;

import java.io.Serializable;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server. 
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no 
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class ChatMessage implements Serializable {

	protected static final long serialVersionUID = 1112122200L;

	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	public static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2, CREATE = 3, INVITE = 4, JOIN = 5, LEAVE = 6, BROADCASTTOALL = 7;
	private int type;
	private String message;
	private int timestamp;
	private int id;
	
	// constructor
	public ChatMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}
	
	public ChatMessage(int type, String message, int timestamp, int id) {
		this.type = type;
		this.message = message;
		this.timestamp = timestamp;
	}
	
	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}
	int getTimeStamp() {
		return this.timestamp;
	}
	int getId() {
		return this.id;
	}
}
