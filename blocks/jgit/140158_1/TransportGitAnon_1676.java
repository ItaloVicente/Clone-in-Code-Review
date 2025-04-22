
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;

public class TransportBundleStream extends Transport implements TransportBundle {
	private InputStream src;

	public TransportBundleStream(final Repository db
			final InputStream in) {
		super(db
		src = in;
	}

	@Override
	public FetchConnection openFetch() throws TransportException {
		if (src == null)
			throw new TransportException(uri
		try {
			return new BundleFetchConnection(this
		} finally {
			src = null;
		}
	}

	@Override
	public PushConnection openPush() throws NotSupportedException {
		throw new NotSupportedException(
				JGitText.get().pushIsNotSupportedForBundleTransport);
	}

	@Override
	public void close() {
		if (src != null) {
			try {
				src.close();
			} catch (IOException err) {
			} finally {
				src = null;
			}
		}
	}
}
