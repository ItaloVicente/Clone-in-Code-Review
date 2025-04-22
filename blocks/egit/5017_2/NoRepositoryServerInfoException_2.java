package org.eclipse.egit.ui.internal.provisional.wizards;

public class NoRepositoryInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRepositoryInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRepositoryInfoException(String message) {
		super(message);
	}

}
