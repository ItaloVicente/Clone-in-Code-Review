package org.eclipse.egit.core.securestorage;

public class UserPasswordCredentials {

	private final String user;
	private final String password;

	public UserPasswordCredentials(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
}
