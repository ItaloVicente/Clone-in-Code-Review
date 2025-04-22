package org.eclipse.jgit.transport;

import org.eclipse.jgit.errors.TransportException;

public class RedirectForbiddenException extends TransportException {

	private static final long serialVersionUID = 1L;

	public RedirectForbiddenException(String message) {
		super(message);
	}
}
