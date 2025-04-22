package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;

public class IndexReadException extends IOException {
	private static final long serialVersionUID = 1L;

	public IndexReadException() {
		super(JGitText.get().indexWriteException);
	}

	public IndexReadException(String s) {
		super(s);
	}

	public IndexReadException(String s
		super(s);
		initCause(cause);
	}
}
