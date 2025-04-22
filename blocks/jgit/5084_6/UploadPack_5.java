
package org.eclipse.jgit.transport;

import java.io.IOException;

public class ServiceMayNotContinueException extends IOException {
	private static final long serialVersionUID = 1L;

	private boolean output;

	public ServiceMayNotContinueException() {
	}

	public ServiceMayNotContinueException(String msg) {
		super(msg);
	}

	public boolean isOutput() {
		return output;
	}

	public void setOutput() {
		output = true;
	}
}
