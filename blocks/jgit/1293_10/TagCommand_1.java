package org.eclipse.jgit.api;

public class InvalidTagNameException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	InvalidTagNameException(String msg) {
		super(msg);
	}
}
