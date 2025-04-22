package org.eclipse.jgit.api.errors;

import org.eclipse.jgit.internal.JGitText;

public class UnmergedPathsException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public UnmergedPathsException() {
		this(null);
	}

	public UnmergedPathsException(Throwable cause) {
		super(JGitText.get().unmergedPaths
	}

	public UnmergedPathsException(String message
		super(message
	}
}
