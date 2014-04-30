package ds.project.server;

import java.util.ArrayList;

public class Group {

	int capacity;
	String groupId;
	String createrId;
	ArrayList<User> groupMembers;
	
	public Group(String groupId, String createrId){
		this.groupId = groupId;
		this.createrId = createrId;
		
	}
}
