package org.eclipse.jgit.api.errors;

public abstract class GitAPIException extends Exception {
	private static final long serialVersionUID = 1L;

	protected GitAPIException(String message
		super(message
	}

	protected GitAPIException(String message) {
		super(message);
	}
}
