package org.eclipse.jgit.api;

import java.util.LinkedList;
import java.util.List;

public class CheckoutConflictException extends GitAPIException {
	private static final long serialVersionUID = 1L;
	private List<String> conflictingPaths;

	CheckoutConflictException(String message
		super(message
	}

	CheckoutConflictException(String message
		super(message
		this.conflictingPaths=conflictingPaths;
	}

	CheckoutConflictException(String message) {
		super(message);
	}

	CheckoutConflictException(String message
		super(message);
		this.conflictingPaths=(conflictingPaths);
	}

	public List<String> getConflictingPathes() {
		return conflictingPaths;
	}

	CheckoutConflictException addConflictingPath(String conflictingPath) {
		if (conflictingPaths==null)
			conflictingPaths=new LinkedList<String>();
		conflictingPaths.add(conflictingPath);
		return this;
	}
}
