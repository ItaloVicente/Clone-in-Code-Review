
package org.eclipse.jgit.internal.storage.reftable;

import java.io.IOException;

import org.eclipse.jgit.lib.ReflogEntry;

public abstract class LogCursor implements AutoCloseable {
	public abstract boolean next() throws IOException;

	public abstract String getRefName();

	public abstract long getUpdateIndex();

	public abstract ReflogEntry getReflogEntry();

	@Override
	public abstract void close();
}
