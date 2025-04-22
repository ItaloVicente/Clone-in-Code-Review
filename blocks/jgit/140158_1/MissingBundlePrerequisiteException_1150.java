package org.eclipse.jgit.errors;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class LockFailedException extends IOException {
	private static final long serialVersionUID = 1L;

	private File file;

	public LockFailedException(File file
		super(message
		this.file = file;
	}

	public LockFailedException(File file
		super(message);
		this.file = file;
	}

	public LockFailedException(File file) {
		this(file
	}

	public File getFile() {
		return file;
	}
}
