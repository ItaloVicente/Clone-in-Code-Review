
package org.eclipse.jgit.errors;

import org.eclipse.jgit.internal.JGitText;

public class RevWalkException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RevWalkException(Throwable cause) {
		super(JGitText.get().walkFailure
	}
}
