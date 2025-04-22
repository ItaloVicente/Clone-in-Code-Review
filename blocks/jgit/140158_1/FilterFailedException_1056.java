package org.eclipse.jgit.api.errors;

public class EmptyCommitException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public EmptyCommitException(String message
		super(message
	}

	public EmptyCommitException(String message) {
		super(message);
	}
}
