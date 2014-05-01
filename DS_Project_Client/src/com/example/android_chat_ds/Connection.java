package com.example.android_chat_ds;

import java.io.Serializable;

public class Connection implements Serializable {
	
	private static final long serialVersionUID = 5347942587740525350L;
	private ConnectServer connections ;
	
	public Connection(){
		connections = new ConnectServer();
	}
	
	public Object fetchMessage(){
		Object msg = connections.receiveMessageFromServer();
		boolean hasMsg = msg instanceof Message;
		if(hasMsg){
			Message m = (Message)msg;
			return m;
		}
		return null;
	}
	
	public boolean createAccount(String membername) {
		
	    connections.connectServer();	
		Object obj1;
		MemberManage member = new MemberManage();	
		member.setMemberName(membername);		
		//member.setPassword(password);	
		
		connections.sendMessageToServer(member);  
		
		while((obj1 = connections.receiveMessageFromServer()) == null);
		System.out.println("Member Name is "+ obj1);
		
		boolean isMember = obj1 instanceof MemberManage;
		boolean isOther = obj1 instanceof String;
		if(isMember){
			MemberManage result = (MemberManage)obj1;
			if(result != null){
				System.out.println("Created an account sucessfully.");
				return true;
			}		
		}
		else if(isOther){
			System.out.println("Account could not created sucessfully.");
		}
		return true; 
	}
	
	public void Login(String membername) throws Exception {
		
		connections.connectServer();	
		Object obj2;		
		MemberManage member = new MemberManage();	
		member.setMemberName(membername);		
		//member.setPassword(password);
		
		connections.sendMessageToServer(member);
		while((obj2 = connections.receiveMessageFromServer()) == null);	
		boolean isMember = obj2 instanceof MemberManage;
		boolean isOther = obj2 instanceof String;
		
		if(isMember){
			//Toast.makeText(getApplicationContext(), "You are logged in successfully.", Toast.LENGTH_SHORT).show();
			System.out.println("You are logged in successfully.");
		}
		else if(isOther){
			System.out.println("User name is incorrect!");
			throw new Exception("Error: Wrong user name!");
		}	
	}

	public boolean createGroup(String groupname,int gId) {	
	    connections.connectServer();	
		Object obj3;
		GroupManage group = new GroupManage(gId);	
		group.setGroupName(groupname);		
		connections.sendMessageToServer(group);  
		
		while((obj3 = connections.receiveMessageFromServer()) == null);
		System.out.println("Group Name is "+ obj3);
		
		boolean isGroup = obj3 instanceof GroupManage;
		boolean isOther = obj3 instanceof String;
		if(isGroup){
			GroupManage result = (GroupManage)obj3;
			if(result != null){
				System.out.println("Created a group sucessfully.");
				return true;
			}		
		}
		else if(isOther){ 
			System.out.println("Group could not created sucessfully.");
		}
		return true; 
	}
}
