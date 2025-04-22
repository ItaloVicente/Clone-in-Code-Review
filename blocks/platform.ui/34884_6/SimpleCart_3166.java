package org.eclipse.jface.examples.databinding.model;

import org.eclipse.jface.examples.databinding.ModelObject;

public class Signon extends ModelObject {

	String userId;
	String password;

	public Signon(String aUserId, String aPassword) {
		userId = aUserId;
		password = aPassword;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String aPassword) {
		String oldValue = password;
		password = aPassword;
		firePropertyChange("password",oldValue,password);
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String aUserId) {
		String oldValue = userId;
		userId = aUserId;
		firePropertyChange("userId",oldValue,userId);
	}

}
