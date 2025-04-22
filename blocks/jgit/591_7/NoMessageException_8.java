package org.eclipse.jgit.api;

public class NoHeadException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	NoHeadException(String message
		super(message
	}

	NoHeadException(String message) {
		super(message);
	}
}
