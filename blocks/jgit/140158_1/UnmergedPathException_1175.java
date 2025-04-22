
package org.eclipse.jgit.errors;

import java.io.IOException;

import org.eclipse.jgit.transport.URIish;

public class TransportException extends IOException {
	private static final long serialVersionUID = 1L;

	public TransportException(URIish uri
	}

	public TransportException(final URIish uri
			final Throwable cause) {
		this(uri.setPass(null) + ": " + s
	}

	public TransportException(String s) {
		super(s);
	}

	public TransportException(String s
		super(s);
		initCause(cause);
	}
}
