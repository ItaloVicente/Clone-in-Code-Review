package org.eclipse.jgit.transport.sshd;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.keyprovider.KeyIdentityProvider;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.junit.ssh.SshTestHarness;
import org.eclipse.jgit.util.FS;
import org.junit.After;
import org.junit.Test;

public class NoFilesSshBuilderTest extends SshTestHarness {

	private PublicKey testServerKey;

	private KeyPair testUserKey;

	@Override
	protected SshSessionFactory createSessionFactory() {
				.setConfigStoreFactory((h
				.setDefaultKeysProvider(f -> new KeyAuthenticator())
				.setServerKeyDatabase((h

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

				.setPreferredAuthentications("publickey")
				.setHomeDirectory(FS.DETECTED.userHome())
				.build(new JGitKeyCache());
	}

	private class KeyAuthenticator
			implements KeyIdentityProvider

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

	@Test
	public void testCloneWithBuiltInKeys() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048);
		testUserKey = generator.generateKeyPair();
		KeyPair hostKey = generator.generateKeyPair();
		server.addHostKey(hostKey
		testServerKey = hostKey.getPublic();
		assertNotNull(testServerKey);
		assertNotNull(testUserKey);
		server.setTestUserPublicKey(testUserKey.getPublic());
		cloneWith(
						+ "/doesntmatter"
				new File(getTemporaryDirectory()
				"Host localhost"
				"IdentityFile "
						+ new File(sshDir
	}

}
