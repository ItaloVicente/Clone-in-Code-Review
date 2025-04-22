package org.eclipse.jgit.api.errors;

import java.util.LinkedList;
import java.util.List;

public class CheckoutConflictException extends GitAPIException {
	private static final long serialVersionUID = 1L;
	private List<String> conflictingPaths;

	public CheckoutConflictException(List<String> conflictingPaths
			org.eclipse.jgit.errors.CheckoutConflictException e) {
		super(e.getMessage()
		this.conflictingPaths = conflictingPaths;
	}

	CheckoutConflictException(String message
		super(message
	}

	CheckoutConflictException(String message
		super(message
		this.conflictingPaths = conflictingPaths;
	}

	CheckoutConflictException(String message) {
		super(message);
	}

	CheckoutConflictException(String message
		super(message);
		this.conflictingPaths = conflictingPaths;
	}

	public List<String> getConflictingPaths() {
		return conflictingPaths;
	}

	CheckoutConflictException addConflictingPath(String conflictingPath) {
		if (conflictingPaths == null)
			conflictingPaths = new LinkedList<>();
		conflictingPaths.add(conflictingPath);
		return this;
	}
}
