
package org.eclipse.jgit.transport;

public class RequestNotYetReadException extends IllegalStateException {
	private static final long serialVersionUID = 1L;

	public RequestNotYetReadException() {
	}

	public RequestNotYetReadException(String msg) {
		super(msg);
	}
}
