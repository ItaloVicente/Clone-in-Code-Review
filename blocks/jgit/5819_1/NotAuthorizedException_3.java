package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

public class AuthenticationNotSupportedException extends TransportException {
	private static final long serialVersionUID = 1L;

	public AuthenticationNotSupportedException(final URIish uri
		super(uri
	}
}
