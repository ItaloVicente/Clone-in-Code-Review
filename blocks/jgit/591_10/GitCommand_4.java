package org.eclipse.jgit.api;

public abstract class GitAPIException extends Exception {
	private static final long serialVersionUID = 1L;

	GitAPIException(String message
		super(message
	}

	GitAPIException(String message) {
		super(message);
	}
}
