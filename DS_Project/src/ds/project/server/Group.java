package ds.project.server;

import java.util.ArrayList;

import ds.project.server.Server.ClientThread;

public class Group {

	public int capacity;
	public String groupId;
	public String createrId;
	public ArrayList<Integer> groupMembers;
	
	public Group(String groupId, String createrId){
		this.groupId = groupId;
		this.createrId = createrId;
		this.groupMembers = new ArrayList<Integer>();
		this.capacity = 1;
		
	}
	
	public boolean addMember(int user) {
		boolean marker = false;
		if (groupMembers.contains(user))
			return marker;
		marker = this.groupMembers.add(user);
		if (marker)
			this.capacity++;
		return marker;
	}
	
	public boolean removeMember(Integer user) {
		boolean marker = false;
		marker = this.groupMembers.remove(user);
		if (marker)
			this.capacity--;
		return marker;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCreaterId() {
		return createrId;
	}

	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}

	public ArrayList<Integer> getGroupMembers() {
		return groupMembers;
	}

	public void setGroupMembers(ArrayList<Integer> groupMembers) {
		this.groupMembers = groupMembers;
	}
}