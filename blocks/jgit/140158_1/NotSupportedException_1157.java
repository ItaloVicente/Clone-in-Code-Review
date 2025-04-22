
package org.eclipse.jgit.errors;

import org.eclipse.jgit.internal.JGitText;

public class NoWorkTreeException extends IllegalStateException {
	private static final long serialVersionUID = 1L;

	public NoWorkTreeException() {
		super(JGitText.get().bareRepositoryNoWorkdirAndIndex);
	}
}
