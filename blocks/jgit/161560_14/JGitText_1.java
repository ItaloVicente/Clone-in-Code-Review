package org.eclipse.jgit.api.errors;

public class ServiceUnavailableException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public ServiceUnavailableException(String message
		super(message
	}

	public ServiceUnavailableException(String message) {
		super(message);
	}
}
