
package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.JGitText;

public class UnpackException extends IOException {
	private static final long serialVersionUID = 1L;

	public UnpackException(Throwable why) {
		super(JGitText.get().unpackException);
		initCause(why);
	}
}
