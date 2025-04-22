package org.eclipse.jgit.api.errors;

public class NoMessageException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public NoMessageException(String message
		super(message
	}

	public NoMessageException(String message) {
		super(message);
	}
}
