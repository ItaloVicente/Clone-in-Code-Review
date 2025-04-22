package org.eclipse.jgit.api.errors;

public class InvalidConfigurationException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public InvalidConfigurationException(String message
		super(message
	}

	public InvalidConfigurationException(String message) {
		super(message);
	}
}
