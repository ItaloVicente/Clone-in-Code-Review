package org.eclipse.jgit.api;


public class InvalidMergeHeadsException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	InvalidMergeHeadsException(String msg) {
		super(msg);
	}
}
