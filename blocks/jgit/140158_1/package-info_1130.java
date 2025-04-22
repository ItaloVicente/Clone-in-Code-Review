
package org.eclipse.jgit.dircache;

import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class InvalidPathException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidPathException(String path) {
		this(JGitText.get().invalidPath
	}

	InvalidPathException(String messagePattern
		super(MessageFormat.format(messagePattern
	}
}
