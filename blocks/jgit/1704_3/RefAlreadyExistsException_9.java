package org.eclipse.jgit.api.errors;

import org.eclipse.jgit.JGitText;

public class NotMergedException extends GitAPIException {
	private static final long serialVersionUID = 1L;

	public NotMergedException() {
		super(JGitText.get().notMergedExceptionMessage);
	}
}
