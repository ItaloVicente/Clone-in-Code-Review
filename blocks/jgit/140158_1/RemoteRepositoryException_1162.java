
package org.eclipse.jgit.errors;

import org.eclipse.jgit.transport.URIish;

public class PackProtocolException extends TransportException {
	private static final long serialVersionUID = 1L;

	public PackProtocolException(URIish uri
	}

	public PackProtocolException(final URIish uri
			final Throwable cause) {
		this(uri + ": " + s
	}

	public PackProtocolException(String s) {
		super(s);
	}

	public PackProtocolException(String s
		super(s);
		initCause(cause);
	}
}
