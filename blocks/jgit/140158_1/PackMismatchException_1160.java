
package org.eclipse.jgit.errors;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;

public class PackInvalidException extends IOException {
	private static final long serialVersionUID = 1L;

	public PackInvalidException(File path) {
		this(path.getAbsolutePath());
	}

	public PackInvalidException(String path) {
		super(MessageFormat.format(JGitText.get().packFileInvalid
	}
}
