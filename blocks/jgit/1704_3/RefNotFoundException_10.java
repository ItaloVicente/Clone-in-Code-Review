package org.eclipse.jgit.api.errors;

import org.eclipse.jgit.lib.Ref;

public class RefAlreadyExistsException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RefAlreadyExistsException(String message) {
		super(message);
	}
}
