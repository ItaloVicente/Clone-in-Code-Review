package org.eclipse.jgit.api.errors;

public class NoHeadException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public NoHeadException(String message
		super(message
	}

	public NoHeadException(String message) {
		super(message);
	}
}
