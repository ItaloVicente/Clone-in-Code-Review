package org.eclipse.egit.ui.internal.provisional.wizards;

import java.net.URI;

import org.eclipse.egit.core.securestorage.UserPasswordCredentials;

public class RepositoryServerInfo {

	private final String label;

	private final URI uri;

	private UserPasswordCredentials credentials;

	public RepositoryServerInfo(String label, URI uri) {
		this.label = label;
		this.uri = uri;
	}

	public String getLabel() {
		return label;
	}

	public URI getUri() {
		return uri;
	}

	public void setCredentials(String user, String password) {
		credentials = new UserPasswordCredentials(user, password);
	}

	public UserPasswordCredentials getCredentials() {
		return credentials;
	}

}
