package org.eclipse.jgit.api.errors;

import org.eclipse.jgit.internal.JGitText;

public class DetachedHeadException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public DetachedHeadException() {
		this(JGitText.get().detachedHeadDetected);
	}

	public DetachedHeadException(String message
		super(message
	}

	public DetachedHeadException(String message) {
		super(message);
	}
}
