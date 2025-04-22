package org.eclipse.jgit.api.errors;

public class EmtpyCommitException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public EmtpyCommitException(String message
		super(message
	}

	public EmtpyCommitException(String message) {
		super(message);
	}
}
