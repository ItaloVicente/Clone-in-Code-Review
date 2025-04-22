package org.eclipse.jgit.api.errors;

public class UnsupportedSigningFormatException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public UnsupportedSigningFormatException(String message
		super(message
	}

	public UnsupportedSigningFormatException(String message) {
		super(message);
	}
}
