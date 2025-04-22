package org.eclipse.egit.core.securestorage;

import java.io.IOException;

import org.eclipse.equinox.security.storage.EncodingUtils;
import org.eclipse.equinox.security.storage.ISecurePreferences;
import org.eclipse.equinox.security.storage.StorageException;
import org.eclipse.jgit.transport.URIish;

public class EGitSecureStore {

	private static final String USER = "user"; //$NON-NLS-1$

	private static final String PASSWORD = "password"; //$NON-NLS-1$

	private static final String GIT_PATH_PREFIX = "/GIT/"; //$NON-NLS-1$

	private final ISecurePreferences preferences;

	public EGitSecureStore(ISecurePreferences preferences) {
		this.preferences = preferences;
	}

	public void putCredentials(URIish uri, UserPasswordCredentials credentials)
			throws StorageException, IOException {
		String pathName = calcNodePath(uri);
		ISecurePreferences node = preferences.node(pathName);
		node.put(USER, credentials.getUser(), false);
		node.put(PASSWORD, credentials.getPassword(), true);
		node.flush();
	}

	public UserPasswordCredentials getCredentials(URIish uri)
			throws StorageException {
		String pathName = calcNodePath(uri);
		if (!preferences.nodeExists(pathName))
			return null;
		ISecurePreferences node = preferences.node(pathName);
		String user = node.get(USER, ""); //$NON-NLS-1$
		String password = node.get(PASSWORD, ""); //$NON-NLS-1$
		if (uri.getUser() != null && !user.equals(uri.getUser()))
			return null;
		return new UserPasswordCredentials(user, password);
	}

	private String calcNodePath(URIish uri) {
		URIish storedURI = uri.setUser(null).setPass(null);
		String pathName = GIT_PATH_PREFIX
				+ EncodingUtils.encodeSlashes(storedURI.toString());
		return pathName;
	}

	public void clearCredentials(URIish uri) throws IOException {
		String pathName = calcNodePath(uri);
		ISecurePreferences node = preferences.node(pathName);
		node.removeNode();
		node.flush();
	}

}
