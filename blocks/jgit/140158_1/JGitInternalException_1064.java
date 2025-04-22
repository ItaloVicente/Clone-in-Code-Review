package org.eclipse.jgit.api.errors;

public class InvalidTagNameException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public InvalidTagNameException(String msg) {
		super(msg);
	}
}
