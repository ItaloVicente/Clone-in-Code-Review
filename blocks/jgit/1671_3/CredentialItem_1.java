package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class UnsupportedCredentialItem extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnsupportedCredentialItem(final URIish uri
		super(uri.setPass(null) + ": " + s);
	}
}
