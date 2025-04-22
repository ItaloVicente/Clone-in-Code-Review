package org.eclipse.jgit.api.errors;

public class UnsupportedGpgFormatException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public UnsupportedGpgFormatException(String message
		super(message
	}

	public UnsupportedGpgFormatException(String message) {
		super(message);
	}
}
