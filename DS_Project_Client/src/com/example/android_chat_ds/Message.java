package com.example.android_chat_ds;

import java.io.Serializable;


public class Message implements Serializable{

	private static final long serialVersionUID = -6915069694826501114L;
	private MemberManage sender;
	private GroupManage receiver;
	private String message;
	
	
	public Message(MemberManage sender, GroupManage receiver, String message){
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
	}

	public MemberManage getUser(){
		return sender;
	}

	public GroupManage getGroup(){
		return receiver;
	}
	
	public String getMessage(){
		return message;
	}	
}