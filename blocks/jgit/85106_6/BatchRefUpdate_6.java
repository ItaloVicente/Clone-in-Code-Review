
package org.eclipse.jgit.internal.ketch;

import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;

class TimeIsUncertainException extends IOException {
	private static final long serialVersionUID = 1L;

	TimeIsUncertainException() {
		super(JGitText.get().timeIsUncertain);
	}

	TimeIsUncertainException(Exception e) {
		super(JGitText.get().timeIsUncertain
	}
}
