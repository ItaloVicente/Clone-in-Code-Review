
package org.eclipse.jgit.transport.resolver;

import org.eclipse.jgit.internal.JGitText;

public class ServiceNotEnabledException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceNotEnabledException(String message
		super(message
	}

	public ServiceNotEnabledException(String message) {
		super(message);
	}

	public ServiceNotEnabledException() {
		super(JGitText.get().serviceNotEnabledNoName);
	}
}
