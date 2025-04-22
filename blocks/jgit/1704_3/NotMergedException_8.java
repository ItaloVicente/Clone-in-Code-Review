package org.eclipse.jgit.api.errors;

public class InvalidRefNameException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public InvalidRefNameException(String msg) {
		super(msg);
	}
}
