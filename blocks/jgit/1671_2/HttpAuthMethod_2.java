package org.eclipse.jgit.transport;

import java.util.Arrays;
import java.util.Set;

import org.eclipse.jgit.errors.UnsupportedCredentialType;

public abstract class CredentialsProvider {
	public enum CredentialType {
		USERNAME

		PASSWORD
	}

	public abstract Set<CredentialType> getSupportedCredentialTypes();

	public boolean supports(CredentialType... types) {
		return getSupportedCredentialTypes().containsAll(Arrays.asList(types));
	}

	public abstract Object getCredentials(URIish uri
			throws UnsupportedCredentialType;
}
