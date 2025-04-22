package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

public class NotAuthorizedException extends TransportException {
	private static final long serialVersionUID = 1L;

	public NotAuthorizedException(final URIish uri
		super(uri
	}
}
