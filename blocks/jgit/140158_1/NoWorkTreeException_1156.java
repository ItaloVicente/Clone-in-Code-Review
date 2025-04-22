
package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

public class NoRemoteRepositoryException extends TransportException {
	private static final long serialVersionUID = 1L;

	public NoRemoteRepositoryException(URIish uri
		super(uri
	}
}
