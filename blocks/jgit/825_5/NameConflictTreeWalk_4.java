package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.JGitText;

public class IndexWriteException extends IOException {
	private static final long serialVersionUID = 1L;

	public IndexWriteException() {
		super(JGitText.get().indexWriteException);
	}

	public IndexWriteException(final String s) {
		super(s);
	}

	public IndexWriteException(final String s
		super(s);
		initCause(cause);
	}
}
