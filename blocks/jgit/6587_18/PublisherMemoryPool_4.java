package org.eclipse.jgit.transport;

import java.io.IOException;

public class PublisherException extends IOException {
	private static final long serialVersionUID = 1L;

	public PublisherException(String message) {
		super(message);
	}

	public PublisherException(String message
		super(message);
		initCause(cause);
	}
}
