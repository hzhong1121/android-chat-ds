package com.example.android_chat_ds;

import java.io.Serializable;
import java.util.ArrayList;


public class GroupManage implements Serializable{

	private static final long serialVersionUID = 4475949339601412334L;
	
	private int gId;
	private String gName;
	private ArrayList<MemberManage> members;
	private ArrayList<GroupManage> chatGroups;
	
	public GroupManage(int gId){
		this.gId = gId;
		members = new ArrayList<MemberManage>();
	}
	
	public void setGroupId(int gid){
		this.gId = gid;
	}

	public void setGroupName(String gname){
		this.gName = gname;
	}
	
	public ArrayList<MemberManage> getMembers(){
		return members;
	}
	
	public int getGroupId(){
		return gId;
	}
	
	public String getGroupName(){
		return gName;
	}

	public void addMember(MemberManage m){
		members.add(m);
	}
	
	public void removeMember(MemberManage m){
		members.remove(m);
	}
	
	public void addGroup(GroupManage group) {
		chatGroups.add(group);
	}

	public void removeGroup(GroupManage group) {
		chatGroups.remove(group);
	}
}
