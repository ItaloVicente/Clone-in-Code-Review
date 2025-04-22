package org.eclipse.jgit.api.errors;

public class InvalidMergeHeadsException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public InvalidMergeHeadsException(String msg) {
		super(msg);
	}
}
