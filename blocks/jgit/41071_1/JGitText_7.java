package org.eclipse.jgit.api.errors;

public class RejectCommitException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RejectCommitException(String message) {
		super(message);
	}
}
