package org.eclipse.jgit.errors;

public class CommandFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	private int returnCode;

	public CommandFailedException(int returnCode
		super(message);
		this.returnCode = returnCode;
	}

	public CommandFailedException(int returnCode
			Throwable cause) {
		super(message
		this.returnCode = returnCode;
	}

	public int getReturnCode() {
		return returnCode;
	}
}
