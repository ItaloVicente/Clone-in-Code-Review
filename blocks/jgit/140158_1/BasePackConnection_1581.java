
package org.eclipse.jgit.transport;

import java.io.OutputStream;
import java.util.Collection;
import java.util.Set;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;

abstract class BaseFetchConnection extends BaseConnection implements
		FetchConnection {
	@Override
	public final void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException {
		fetch(monitor
	}

	@Override
	public final void fetch(final ProgressMonitor monitor
			final Collection<Ref> want
			OutputStream out) throws TransportException {
		markStartedOperation();
		doFetch(monitor
	}

	@Override
	public boolean didFetchIncludeTags() {
		return false;
	}

	protected abstract void doFetch(final ProgressMonitor monitor
			final Collection<Ref> want
			throws TransportException;
}
