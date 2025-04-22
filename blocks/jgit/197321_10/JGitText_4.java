package org.eclipse.jgit.api.errors;

public class FileNotFoundException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public FileNotFoundException(String message
		super(message
	}

	public FileNotFoundException(String message) {
		super(message);
	}
}
