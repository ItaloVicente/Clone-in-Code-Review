package org.eclipse.jgit.api.errors;

public class RefAlreadyExistsException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RefAlreadyExistsException(String message) {
		super(message);
	}
}
