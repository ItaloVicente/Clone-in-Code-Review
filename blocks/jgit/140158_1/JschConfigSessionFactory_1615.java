
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UncheckedIOException;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;

class InternalPushConnection<C> extends BasePackPushConnection {
	private Thread worker;

	public InternalPushConnection(PackTransport transport
			final ReceivePackFactory<C> receivePackFactory
			final C req
		super(transport);

		final PipedInputStream in_r;
		final PipedOutputStream in_w;

		final PipedInputStream out_r;
		final PipedOutputStream out_w;
		try {
			in_r = new PipedInputStream();
			in_w = new PipedOutputStream(in_r);

			out_r = new PipedInputStream();
			out_w = new PipedOutputStream(out_r);
		} catch (IOException err) {
			remote.close();
			throw new TransportException(uri
		}

			@Override
			public void run() {
				try {
					final ReceivePack rp = receivePackFactory.create(req
					rp.receive(out_r
				} catch (ServiceNotEnabledException | ServiceNotAuthorizedException e) {
				}
                             catch (IOException e) {
					throw new UncheckedIOException(e);
				} finally {
					try {
						out_r.close();
					} catch (IOException e2) {
					}

					try {
						in_w.close();
					} catch (IOException e2) {
					}

					remote.close();
				}
			}
		};
		worker.start();

		init(in_r
		readAdvertisedRefs();
	}

	@Override
	public void close() {
		super.close();

		if (worker != null) {
			try {
				worker.join();
			} catch (InterruptedException ie) {
			} finally {
				worker = null;
			}
		}
	}
}
