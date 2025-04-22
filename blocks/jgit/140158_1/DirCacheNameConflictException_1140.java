
package org.eclipse.jgit.errors;

public class DiffInterruptedException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DiffInterruptedException(String message
		super(message
	}

	public DiffInterruptedException(String message) {
		super(message);
	}

	public DiffInterruptedException() {
		super();
	}
}
