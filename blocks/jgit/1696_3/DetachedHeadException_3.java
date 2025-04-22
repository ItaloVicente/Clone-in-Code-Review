package org.eclipse.jgit.api.errors;

public class CanceledException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public CanceledException(String message) {
		super(message);
	}
}
