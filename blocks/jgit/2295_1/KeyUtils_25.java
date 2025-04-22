
package org.eclipse.jgit.storage.dht;

import java.util.concurrent.TimeoutException;

import org.eclipse.jgit.storage.dht.spi.Database;

public class DhtTimeoutException extends DhtException {
	private static final long serialVersionUID = 1L;

	public DhtTimeoutException(TimeoutException cause) {
		super(cause.getMessage());
		initCause(cause);
	}

	public DhtTimeoutException(InterruptedException cause) {
		super(cause.getMessage());
		initCause(cause);
	}
}
