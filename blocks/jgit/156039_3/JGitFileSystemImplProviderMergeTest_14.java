package org.eclipse.jgit.niofs.internal;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetAddress;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;

import org.junit.Test;

import static java.net.Authenticator.requestPasswordAuthentication;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_DAEMON_ENABLED;
import static org.eclipse.jgit.niofs.internal.JGitFileSystemProviderConfiguration.GIT_SSH_ENABLED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JGitFileSystemImplProviderHttpProxyTest {

	@Test
	public void testHttpProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put("http.proxyUser"
				put("http.proxyPassword"
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
				8080

		assertEquals(userName
		assertEquals(passw

		provider.dispose();
	}

	@Test
	public void testHttpsProxy() throws IOException {
		final String userName = "user";
		final String passw = "passwd";

		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put("https.proxyUser"
				put("https.proxyPassword"
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
				8080

		assertEquals(userName
		assertEquals(passw

		provider.dispose();
	}

	@Test
	public void testNoProxyInfo() throws IOException {
		final JGitFileSystemProvider provider = new JGitFileSystemProvider(new HashMap<String
			{
				put(GIT_DAEMON_ENABLED
				put(GIT_SSH_ENABLED
			}
		});

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
					InetAddress.getLocalHost()
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		{
			final PasswordAuthentication passwdAuth = requestPasswordAuthentication("localhost"
					InetAddress.getLocalHost()
					Authenticator.RequestorType.PROXY);

			assertNull(passwdAuth);
		}

		provider.dispose();
	}
}
