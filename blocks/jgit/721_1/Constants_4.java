package org.eclipse.jgit.api;

public class WrongRepoStateException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	WrongRepoStateException(String message
		super(message
	}

	WrongRepoStateException(String message) {
		super(message);
	}
}
