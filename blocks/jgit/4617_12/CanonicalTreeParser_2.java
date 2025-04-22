
package org.eclipse.jgit.dircache;

import java.text.MessageFormat;

import org.eclipse.jgit.JGitText;

public class InvalidPathException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public InvalidPathException(String path) {
		super(MessageFormat.format(JGitText.get().invalidPath
	}
}
