
package org.eclipse.jgit.util.time;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class ProposedTimestamp implements AutoCloseable {
	public abstract long read(TimeUnit unit);

	public abstract void blockUntil(long timeout
			throws InterruptedException

	public long getMillis() {
		return read(TimeUnit.MILLISECONDS);
	}

	@Override
	public void close() {
	}
}
