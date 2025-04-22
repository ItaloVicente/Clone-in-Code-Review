package org.eclipse.jgit.api;

import org.eclipse.jgit.lib.RefUpdate;

public class ConcurrentRefUpdateException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	ConcurrentRefUpdateException(String message
		super(message
	}

	ConcurrentRefUpdateException(String message) {
		super(message);
	}
}
