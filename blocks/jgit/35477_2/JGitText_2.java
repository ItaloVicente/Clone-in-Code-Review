package org.eclipse.jgit.api.errors;

public class RejectedCommitException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RejectedCommitException(String message) {
		super(message);
	}
}
