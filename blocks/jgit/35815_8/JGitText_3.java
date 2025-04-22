package org.eclipse.jgit.api.errors;

public class RejectRebaseException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RejectRebaseException(String message) {
		super(message);
	}
}
