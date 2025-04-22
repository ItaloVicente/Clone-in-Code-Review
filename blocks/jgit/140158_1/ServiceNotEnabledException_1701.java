
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.internal.JGitText;

public class ServiceNotAuthorizedException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceNotAuthorizedException(String message
		super(message
	}

	public ServiceNotAuthorizedException(String message) {
		super(message);
	}

	public ServiceNotAuthorizedException() {
		super(JGitText.get().unauthorized);
	}
}
