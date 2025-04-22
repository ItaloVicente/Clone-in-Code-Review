
package org.eclipse.jgit.util;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class CachedAuthenticator extends Authenticator {
	private static final Collection<CachedAuthentication> cached = new CopyOnWriteArrayList<CachedAuthentication>();

	public static void add(final CachedAuthentication ca) {
		cached.add(ca);
	}

	@Override
	protected final PasswordAuthentication getPasswordAuthentication() {
		final String host = getRequestingHost();
		final int port = getRequestingPort();
		for (final CachedAuthentication ca : cached) {
			if (ca.host.equals(host) && ca.port == port)
				return ca.toPasswordAuthentication();
		}
		PasswordAuthentication pa = promptPasswordAuthentication();
		if (pa != null) {
			CachedAuthentication ca = new CachedAuthentication(host
					.getUserName()
			add(ca);
			return ca.toPasswordAuthentication();
		}
		return null;
	}

	protected abstract PasswordAuthentication promptPasswordAuthentication();

	public static class CachedAuthentication {
		final String host;

		final int port;

		final String user;

		final String pass;

		public CachedAuthentication(final String aHost
				final String aUser
			host = aHost;
			port = aPort;
			user = aUser;
			pass = aPass;
		}

		PasswordAuthentication toPasswordAuthentication() {
			return new PasswordAuthentication(user
		}
	}
}
