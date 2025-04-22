
package org.eclipse.jgit.transport;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;

class InternalFetchConnection<C> extends BasePackFetchConnection {
	private Thread worker;

	public InternalFetchConnection(PackTransport transport
			final UploadPackFactory<C> uploadPackFactory
			final C req
		super(transport);

		final PipedInputStream in_r;
		final PipedOutputStream in_w;

		final PipedInputStream out_r;
		final PipedOutputStream out_w;
		try {
			in_r = new PipedInputStream();
			in_w = new PipedOutputStream(in_r);

			out_r = new PipedInputStream() {
				{
					buffer = new byte[MIN_CLIENT_BUFFER];
				}
			};
			out_w = new PipedOutputStream(out_r);
		} catch (IOException err) {
			remote.close();
			throw new TransportException(uri
		}

			public void run() {
				try {
					final UploadPack rp = uploadPackFactory.create(req
					rp.upload(out_r
				} catch (ServiceNotEnabledException e) {
				} catch (ServiceNotAuthorizedException e) {
				} catch (IOException err) {
					err.printStackTrace();
				} catch (RuntimeException err) {
					err.printStackTrace();
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

		try {
			if (worker != null) {
				worker.join();
			}
		} catch (InterruptedException ie) {
		} finally {
			worker = null;
		}
	}
}
