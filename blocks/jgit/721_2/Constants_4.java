package org.eclipse.jgit.api;

public class WrongRepositoryStateException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	WrongRepositoryStateException(String message
		super(message
	}

	WrongRepositoryStateException(String message) {
		super(message);
	}
}
