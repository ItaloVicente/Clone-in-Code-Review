package org.eclipse.jgit.api.errors;

public class JGitInternalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public JGitInternalException(String message
		super(message
	}

	public JGitInternalException(String message) {
		super(message);
	}
}
