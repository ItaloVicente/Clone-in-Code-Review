
package org.eclipse.jgit.storage.dht;

import java.io.IOException;

import org.eclipse.jgit.storage.dht.spi.Database;

public class DhtException extends IOException {
	private static final long serialVersionUID = 1L;

	public DhtException(String message) {
		super(message);
	}

	public DhtException(Throwable cause) {
		super(cause.getMessage());
		initCause(cause);
	}

	public DhtException(String message
		super(message);
		initCause(cause);
	}

	public static class TODO extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public TODO(String what) {
			super(what);
		}
	}
}
