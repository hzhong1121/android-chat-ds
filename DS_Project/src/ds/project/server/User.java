package ds.project.server;

import java.io.Serializable;


public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5545819888452644399L;
	
	boolean isCreator;
	String userId;
	
	public User(boolean isCreator, String userId) {
		this.isCreator = isCreator;
		this.userId = userId;
	}
	
	@Override
	public int hashCode() {
		return userId.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o.getClass() != User.class) return false;
		User user = (User) o;

		return (this.userId==user.userId);
	}

	public boolean isCreator() {
		return isCreator;
	}

	public void setCreator(boolean isCreator) {
		this.isCreator = isCreator;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
