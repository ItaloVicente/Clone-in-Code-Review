package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.future.DefaultConnectFuture;
import org.apache.sshd.client.session.ClientSessionImpl;
import org.apache.sshd.client.session.SessionFactory;
import org.apache.sshd.common.future.SshFutureListener;
import org.apache.sshd.common.io.IoConnectFuture;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.keyprovider.FileKeyPairProvider;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.apache.sshd.common.session.helpers.AbstractSession;
import org.apache.sshd.common.util.ValidateUtils;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.transport.sshd.KeyCache;

public class JGitSshClient extends SshClient {

	private KeyCache keyCache;

	private CredentialsProvider credentialsProvider;

	@Override
	protected SessionFactory createSessionFactory() {
		return new JGitSessionFactory(this);
	}

	@Override
	public ConnectFuture connect(HostConfigEntry hostConfig)
			throws IOException {
		if (connector == null) {
		}
		Objects.requireNonNull(hostConfig
		String host = ValidateUtils.checkNotNullAndNotEmpty(
				hostConfig.getHostName()
		int port = hostConfig.getPort();
		ValidateUtils.checkTrue(port > 0
		String userName = hostConfig.getUsername();
		InetSocketAddress address = new InetSocketAddress(host
		ConnectFuture connectFuture = new DefaultConnectFuture(
				userName + '@' + address
		SshFutureListener<IoConnectFuture> listener = createConnectCompletionListener(
				connectFuture
		connector.connect(address).addListener(listener);
		return connectFuture;
	}

	private SshFutureListener<IoConnectFuture> createConnectCompletionListener(
			ConnectFuture connectFuture
			InetSocketAddress address
		return new SshFutureListener<IoConnectFuture>() {

			@Override
			public void operationComplete(IoConnectFuture future) {
				if (future.isCanceled()) {
					connectFuture.cancel();
					return;
				}
				Throwable t = future.getException();
				if (t != null) {
					connectFuture.setException(t);
					return;
				}
				IoSession ioSession = future.getSession();
				try {
					JGitClientSession session = createSession(ioSession
							username
					connectFuture.setSession(session);
				} catch (RuntimeException e) {
					connectFuture.setException(e);
					ioSession.close(true);
				}
			}

			@Override
			public String toString() {
						+ '@' + address + ']';
			}
		};
	}

	private JGitClientSession createSession(IoSession ioSession
			String username
			HostConfigEntry hostConfig) {
		AbstractSession rawSession = AbstractSession.getSession(ioSession);
		if (!(rawSession instanceof JGitClientSession)) {
					+ rawSession.getClass().getCanonicalName());
		}
		JGitClientSession session = (JGitClientSession) rawSession;
		session.setUsername(username);
		session.setConnectAddress(address);
		session.setHostConfigEntry(hostConfig);
		if (session.getCredentialsProvider() == null) {
			session.setCredentialsProvider(getCredentialsProvider());
		}
		FileKeyPairProvider ourConfiguredKeysProvider = null;
		List<Path> identities = hostConfig.getIdentities().stream()
				.map(s -> {
					try {
						return Paths.get(s);
					} catch (InvalidPathException e) {
						log.warn(format(SshdText.get().configInvalidPath
								SshConstants.IDENTITY_FILE
						return null;
					}
				}).filter(p -> p != null && Files.exists(p))
				.collect(Collectors.toList());
		ourConfiguredKeysProvider = new CachingKeyPairProvider(identities
				keyCache);
		ourConfiguredKeysProvider.setPasswordFinder(getFilePasswordProvider());
		if (hostConfig.isIdentitiesOnly()) {
			session.setKeyPairProvider(ourConfiguredKeysProvider);
		} else {
			KeyPairProvider defaultKeysProvider = getKeyPairProvider();
			if (defaultKeysProvider instanceof FileKeyPairProvider) {
				((FileKeyPairProvider) defaultKeysProvider)
						.setPasswordFinder(getFilePasswordProvider());
			}
			KeyPairProvider combinedProvider = new CombinedKeyPairProvider(
					ourConfiguredKeysProvider
			session.setKeyPairProvider(combinedProvider);
		}
		return session;
	}

	public void setKeyCache(KeyCache cache) {
		keyCache = cache;
	}

	public void setCredentialsProvider(CredentialsProvider provider) {
		credentialsProvider = provider;
	}

	public CredentialsProvider getCredentialsProvider() {
		return credentialsProvider;
	}

	private static class JGitSessionFactory extends SessionFactory {

		public JGitSessionFactory(JGitSshClient client) {
			super(client);
		}

		@Override
		protected ClientSessionImpl doCreateSession(IoSession ioSession)
				throws Exception {
			return new JGitClientSession(getClient()
		}
	}

	private static class CombinedKeyPairProvider implements KeyPairProvider {

		private final List<KeyPairProvider> providers;

		public CombinedKeyPairProvider(KeyPairProvider... providers) {
			this(Arrays.stream(providers).filter(Objects::nonNull)
					.collect(Collectors.toList()));
		}

		public CombinedKeyPairProvider(List<KeyPairProvider> providers) {
			this.providers = providers;
		}

		@Override
		public Iterable<String> getKeyTypes() {
			throw new UnsupportedOperationException(
		}

		@Override
		public KeyPair loadKey(String type) {
			throw new UnsupportedOperationException(
		}

		@Override
		public Iterable<KeyPair> loadKeys() {
			return () -> new Iterator<KeyPair>() {

				private Iterator<KeyPairProvider> factories = providers.iterator();
				private Iterator<KeyPair> current;

				private Boolean hasElement;

				@Override
				public boolean hasNext() {
					if (hasElement != null) {
						return hasElement.booleanValue();
					}
					while (current == null || !current.hasNext()) {
						if (factories.hasNext()) {
							current = factories.next().loadKeys().iterator();
						} else {
							current = null;
							hasElement = Boolean.FALSE;
							return false;
						}
					}
					hasElement = Boolean.TRUE;
					return true;
				}

				@Override
				public KeyPair next() {
					if (hasElement == null && !hasNext()
							|| !hasElement.booleanValue()) {
						throw new NoSuchElementException();
					}
					hasElement = null;
					KeyPair result;
					try {
						result = current.next();
					} catch (NoSuchElementException e) {
						result = null;
					}
					return result;
				}

			};
		}

	}
}
