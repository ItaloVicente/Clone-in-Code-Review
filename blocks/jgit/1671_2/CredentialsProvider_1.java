package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.CredentialsProvider.CredentialType;
import org.eclipse.jgit.transport.URIish;

public class UnsupportedCredentialType extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnsupportedCredentialType(final URIish uri
		super(uri.setPass(null) + ": " + s);
	}

}
