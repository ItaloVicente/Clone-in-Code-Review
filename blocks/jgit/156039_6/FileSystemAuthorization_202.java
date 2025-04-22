package org.eclipse.jgit.niofs.internal.security;

public interface AuthenticationService {

	User login(String username

	boolean isLoggedIn();

	void logout();

	User getUser();
}
