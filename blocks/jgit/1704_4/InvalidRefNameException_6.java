package org.eclipse.jgit.api.errors;

public class CannotDeleteCurrentBranchException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public CannotDeleteCurrentBranchException(String message) {
		super(message);
	}
}
