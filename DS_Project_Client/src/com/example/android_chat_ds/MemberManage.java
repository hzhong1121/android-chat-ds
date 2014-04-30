package com.example.android_chat_ds;

import java.io.Serializable;
import java.util.ArrayList;

public class MemberManage implements Serializable {

	private static final long serialVersionUID = -995721925337436548L;

	private int mId;
	private String mName, pwd;

	private ArrayList<GroupManage> chatGroups;
	//private ArrayList<MemberManage> members;
	private boolean isLogin;

	public MemberManage() {
		isLogin = false;
		chatGroups = new ArrayList<GroupManage>();
	}
	
	public ArrayList<GroupManage> getGroups() {
		return chatGroups;
	}
	
	public boolean logIn() {
		return isLogin;
	}
	
	public int getMemberId() {
		return mId;
	}

	public String getMemberName() {
		return mName;
	}

	/*public String getPassword() {
		return pwd;
	}*/

	public void setMemberId(int mid) {
		this.mId = mid;
	}

	public void setMemberName(String mName) {
		this.mName = mName;
	}
	
	public void setLogin(boolean y) {
		isLogin = y;
	}

	/*public void setPassword(String password) {
		this.pwd = password;
	}*/
	
	public void addGroup(GroupManage group) {
		chatGroups.add(group);
	}

	public void removeGroup(GroupManage group) {
		chatGroups.remove(group);
	}
}
