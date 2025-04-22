package org.eclipse.jgit.lfs.errors;

public class LfsException extends Exception {
	private static final long serialVersionUID = 1L;

	public LfsException(String message) {
		super(message);
	}
}
