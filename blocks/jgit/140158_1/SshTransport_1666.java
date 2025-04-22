
package org.eclipse.jgit.transport;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.SystemReader;

public abstract class SshSessionFactory {
	private static SshSessionFactory INSTANCE = new DefaultSshSessionFactory();

	public static SshSessionFactory getInstance() {
		return INSTANCE;
	}

	public static void setInstance(SshSessionFactory newFactory) {
		if (newFactory != null)
			INSTANCE = newFactory;
		else
			INSTANCE = new DefaultSshSessionFactory();
	}

	public static String getLocalUserName() {
		return AccessController.doPrivileged(new PrivilegedAction<String>() {
			@Override
			public String run() {
				return SystemReader.getInstance()
						.getProperty(Constants.OS_USER_NAME_KEY);
			}
		});
	}

	public abstract RemoteSession getSession(URIish uri
			CredentialsProvider credentialsProvider
			throws TransportException;

	public void releaseSession(RemoteSession session) {
		session.disconnect();
	}
}
