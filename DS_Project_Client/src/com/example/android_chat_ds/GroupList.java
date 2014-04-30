package com.example.android_chat_ds;

import java.util.ArrayList;  

public class GroupList {  

     private String groupname;  					
     private ArrayList<MemberList> users;  

     public String getName() {  
         return groupname;  
     }  

     public void setName(String name) {  
         this.groupname = name;  
     }  

     public ArrayList<MemberList> getGroupUser() {  
         return users;  
     }  
     
     public void setContent(ArrayList<MemberList> user) {  
		this.users = user;  
     }          

} 
