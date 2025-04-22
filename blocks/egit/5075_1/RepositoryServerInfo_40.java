package org.eclipse.egit.ui.internal.provisional.wizards;

public class NoRepositoryServerInfoException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoRepositoryServerInfoException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoRepositoryServerInfoException(String message) {
		super(message);
	}

}
