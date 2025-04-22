package org.eclipse.jgit.api.errors;

public class WrongRepositoryStateException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public WrongRepositoryStateException(String message
		super(message
	}

	public WrongRepositoryStateException(String message) {
		super(message);
	}
}
