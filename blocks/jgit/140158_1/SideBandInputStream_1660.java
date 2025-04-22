
package org.eclipse.jgit.transport;

import java.io.IOException;

import org.eclipse.jgit.internal.JGitText;

public class ServiceMayNotContinueException extends IOException {
	private static final int FORBIDDEN = 403;
	private static final long serialVersionUID = 1L;

	private final int statusCode;
	private boolean output;

	public ServiceMayNotContinueException() {
		statusCode = FORBIDDEN;
	}

	public ServiceMayNotContinueException(String msg) {
		super(msg);
		statusCode = FORBIDDEN;
	}

	public ServiceMayNotContinueException(String msg
		super(msg);
		this.statusCode = statusCode;
	}

	public ServiceMayNotContinueException(String msg
		super(msg
		statusCode = FORBIDDEN;
	}

	public ServiceMayNotContinueException(
			String msg
		super(msg
		this.statusCode = statusCode;
	}

	public ServiceMayNotContinueException(Throwable cause) {
		this(JGitText.get().internalServerError
	}

	public boolean isOutput() {
		return output;
	}

	public void setOutput() {
		output = true;
	}

	public int getStatusCode() {
		return statusCode;
	}
}
