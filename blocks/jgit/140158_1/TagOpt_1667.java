
package org.eclipse.jgit.transport;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.util.FS;

public abstract class SshTransport extends TcpTransport {

	private SshSessionFactory sch;

	private RemoteSession sock;

	protected SshTransport(Repository local
		super(local
		sch = SshSessionFactory.getInstance();
	}

	protected SshTransport(URIish uri) {
		super(uri);
		sch = SshSessionFactory.getInstance();
	}

	public void setSshSessionFactory(SshSessionFactory factory) {
		if (factory == null)
			throw new NullPointerException(JGitText.get().theFactoryMustNotBeNull);
		if (sock != null)
			throw new IllegalStateException(
					JGitText.get().anSSHSessionHasBeenAlreadyCreated);
		sch = factory;
	}

	public SshSessionFactory getSshSessionFactory() {
		return sch;
	}

	protected RemoteSession getSession() throws TransportException {
		if (sock != null)
			return sock;

		final int tms = getTimeout() > 0 ? getTimeout() * 1000 : 0;

		final FS fs = local == null ? FS.detect() : local.getFS();

		sock = sch
				.getSession(uri
		return sock;
	}

	@Override
	public void close() {
		if (sock != null) {
			try {
				sch.releaseSession(sock);
			} finally {
				sock = null;
			}
		}
	}
}
