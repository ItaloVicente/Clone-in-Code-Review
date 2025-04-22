package org.eclipse.jgit.util.fs;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;

public class AccessDeniedException extends IOException {
	private static final long serialVersionUID = 1L;

	public AccessDeniedException(String file) {
		super(file);
	}

	public AccessDeniedException(String file
		super(MessageFormat.format(JGitText.get().accessFileDenied
				file
	}
}
