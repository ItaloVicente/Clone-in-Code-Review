package org.eclipse.jgit.api.errors;

public class RefNotFoundException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public RefNotFoundException(String message
		super(message
	}

	public RefNotFoundException(String message) {
		super(message);
	}
}
