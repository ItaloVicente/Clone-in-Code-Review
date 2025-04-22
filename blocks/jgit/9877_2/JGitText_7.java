package org.eclipse.jgit.api.errors;

import org.eclipse.jgit.api.errors.GitAPIException;

public class StashApplyFailureException extends GitAPIException {

	public StashApplyFailureException(final String message) {
		super(message);
	}

}
