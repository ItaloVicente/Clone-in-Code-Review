
package org.eclipse.jgit.errors;

import org.eclipse.jgit.JGitText;
import org.eclipse.jgit.lib.Repository;

public class NoWorkTreeException extends IllegalStateException {
	private static final long serialVersionUID = 1L;

	public NoWorkTreeException() {
		super(JGitText.get().bareRepositoryNoWorkdirAndIndex);
	}
}
