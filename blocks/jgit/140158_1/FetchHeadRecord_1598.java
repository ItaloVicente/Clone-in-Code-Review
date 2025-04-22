
package org.eclipse.jgit.transport;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.storage.file.PackLock;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;

public interface FetchConnection extends Connection {
	void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException;

	void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			OutputStream out) throws TransportException;

	boolean didFetchIncludeTags();

	boolean didFetchTestConnectivity();

	void setPackLockMessage(String message);

	Collection<PackLock> getPackLocks();
}
