package org.eclipse.jgit.transport.sshd;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.eclipse.jgit.transport.URIish;

public interface KeyPasswordProvider {

	char[] getPassphrase(URIish uri

	void setAttempts(int maxNumberOfAttempts);

	default int getAttempts() {
		return 1;
	}

	boolean keyLoaded(URIish uri
			throws IOException
}
