package org.eclipse.jgit.util.fs;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;

public class NoSuchFileException extends IOException {
	private static final long serialVersionUID = 1L;

	public NoSuchFileException(String file) {
		super(file);
	}

	public NoSuchFileException(String file
		super(MessageFormat.format(JGitText.get().noSuchFile
				reason));
	}
}
