package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;

public class IndexWriteException extends IOException {
	private static final long serialVersionUID = 1L;

	public IndexWriteException() {
		super(JGitText.get().indexWriteException);
	}

	public IndexWriteException(String s) {
		super(s);
	}

	public IndexWriteException(String s
		super(s);
		initCause(cause);
	}
}
