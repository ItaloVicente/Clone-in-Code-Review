package org.eclipse.jgit.api.errors;

public class RejectedRebaseException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RejectedRebaseException(String message) {
		super(message);
	}
}
