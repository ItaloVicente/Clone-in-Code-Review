package org.eclipse.jgit.transport.sshd;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.sshd.common.NamedResource;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.keyprovider.KeyIdentityProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.ssh.SshTestHarness;
import org.eclipse.jgit.util.FS;
import org.junit.After;
import org.junit.Test;

public class NoFilesSshTest extends SshTestHarness {


	private PublicKey testServerKey;

	private KeyPair testUserKey;

	@Override
	protected SshSessionFactory createSessionFactory() {
		SshdSessionFactory result = new SshdSessionFactory(new JGitKeyCache()
				null) {

			@Override
			protected File getSshConfig(File dir) {
				return null;
			}

			@Override
			protected ServerKeyDatabase getServerKeyDatabase(File homeDir
					File dir) {
				return new ServerKeyDatabase() {

					@Override
					public List<PublicKey> lookup(String connectAddress
							InetSocketAddress remoteAddress
							Configuration config) {
						return Collections.singletonList(testServerKey);
					}

					@Override
					public boolean accept(String connectAddress
							InetSocketAddress remoteAddress
							PublicKey serverKey
							CredentialsProvider provider) {
						return KeyUtils.compareKeys(serverKey
					}

				};
			}

			@Override
			protected Iterable<KeyPair> getDefaultKeys(File dir) {
				return new KeyAuthenticator();
			}

			@Override
			protected String getDefaultPreferredAuthentications() {
				return "publickey";
			}
		};

		result.setHomeDirectory(FS.DETECTED.userHome());
		result.setSshDirectory(sshDir);
		return result;
	}

	private class KeyAuthenticator implements KeyIdentityProvider

		@Override
		public Iterator<KeyPair> iterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterable<KeyPair> loadKeys(SessionContext session)
				throws IOException
			if (!TEST_USER.equals(session.getUsername())) {
				return Collections.emptyList();
			}
			SshdSocketAddress remoteAddress = SshdSocketAddress
					.toSshdSocketAddress(session.getRemoteAddress());
			switch (remoteAddress.getHostName()) {
			case "localhost":
			case "127.0.0.1":
				return Collections.singletonList(testUserKey);
			default:
				return Collections.emptyList();
			}
		}
	}

	@After
	public void cleanUp() {
		testServerKey = null;
		testUserKey = null;
	}

	@Override
	protected void installConfig(String... config) {
		File configFile = new File(sshDir
		if (config != null) {
			try {
				Files.write(configFile.toPath()
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	private KeyPair load(Path path) throws Exception {
		try (InputStream in = Files.newInputStream(path)) {
			return SecurityUtils
					.loadKeyPairIdentities(null
							NamedResource.ofName(path.toString())
					.iterator().next();
		}
	}

	@Test
	public void testCloneWithBuiltInKeys() throws Exception {
		File newHostKey = new File(getTemporaryDirectory()
		copyTestResource("id_ed25519"
		server.addHostKey(newHostKey.toPath()
		testServerKey = load(newHostKey.toPath()).getPublic();
		assertTrue(newHostKey.delete());
		testUserKey = load(privateKey1.getAbsoluteFile().toPath());
		assertNotNull(testServerKey);
		assertNotNull(testUserKey);
		cloneWith(
						+ "/doesntmatter"
				new File(getTemporaryDirectory()
				"Host localhost"
				"IdentityFile "
						+ new File(sshDir
	}

}
