package org.eclipse.jgit.api.errors;

public class MultipleParentsNotAllowedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public MultipleParentsNotAllowedException(String message
		super(message
	}

	public MultipleParentsNotAllowedException(String message) {
		super(message);
	}
}
