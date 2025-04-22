package org.eclipse.jgit.api.errors;

public class NoSuchParentException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public NoSuchParentException(String message
		super(message
	}

	public NoSuchParentException(String message) {
		super(message);
	}
}
