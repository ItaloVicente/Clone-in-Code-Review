package org.eclipse.jgit.api.errors;

public class PatchConflictException extends PatchApplyException {
	private static final long serialVersionUID = 1L;

	public PatchConflictException(String message
		super(message
	}

	public PatchConflictException(String message) {
		super(message);
	}

}
