package org.eclipse.jgit.transport.sshd;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import org.apache.sshd.client.ClientBuilder;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.auth.UserAuth;
import org.apache.sshd.client.auth.keyboard.UserAuthKeyboardInteractiveFactory;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.client.keyverifier.ServerKeyVerifier;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.compression.BuiltinCompressions;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.apache.sshd.common.keyprovider.KeyPairProvider;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.transport.sshd.CachingKeyPairProvider;
import org.eclipse.jgit.internal.transport.sshd.GssApiWithMicAuthFactory;
import org.eclipse.jgit.internal.transport.sshd.JGitPasswordAuthFactory;
import org.eclipse.jgit.internal.transport.sshd.JGitPublicKeyAuthFactory;
import org.eclipse.jgit.internal.transport.sshd.JGitSshClient;
import org.eclipse.jgit.internal.transport.sshd.JGitSshConfig;
import org.eclipse.jgit.internal.transport.sshd.JGitUserInteraction;
import org.eclipse.jgit.internal.transport.sshd.OpenSshServerKeyVerifier;
import org.eclipse.jgit.internal.transport.sshd.PasswordProviderWrapper;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.FS;

public class SshdSessionFactory extends SshSessionFactory implements Closeable {

	private final AtomicBoolean closing = new AtomicBoolean();

	private final Set<SshdSession> sessions = new HashSet<>();

	private final Map<Tuple

	private final Map<Tuple

	private final Map<Tuple

	private final KeyCache keyCache;

	private final ProxyDataFactory proxies;

	private File sshDirectory;

	private File homeDirectory;

	public SshdSessionFactory() {
		this(null
	}

	public SshdSessionFactory(KeyCache keyCache
		super();
		this.keyCache = keyCache;
		this.proxies = proxies;
	}

	private static final class Tuple {
		private Object[] objects;

		public Tuple(Object[] objects) {
			this.objects = objects;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == this) {
				return true;
			}
			if (obj != null && obj.getClass() == Tuple.class) {
				Tuple other = (Tuple) obj;
				return Arrays.equals(objects
			}
			return false;
		}

		@Override
		public int hashCode() {
			return Arrays.hashCode(objects);
		}
	}


	@Override
	public SshdSession getSession(URIish uri
			CredentialsProvider credentialsProvider
			throws TransportException {
		SshdSession session = null;
		try {
			session = new SshdSession(uri
				File home = getHomeDirectory();
				if (home == null) {
					home = FS.DETECTED.userHome();
				}
				File sshDir = getSshDirectory();
				if (sshDir == null) {
					sshDir = new File(home
				}
				HostConfigEntryResolver configFile = getHostConfigEntryResolver(
						home
				KeyPairProvider defaultKeysProvider = toKeyPairProvider(
						getDefaultKeys(sshDir));
				KeyPasswordProvider passphrases = createKeyPasswordProvider(
						credentialsProvider);
				SshClient client = ClientBuilder.builder()
						.factory(JGitSshClient::new)
						.filePasswordProvider(
								createFilePasswordProvider(passphrases))
						.hostConfigEntryResolver(configFile)
						.serverKeyVerifier(getServerKeyVerifier(home
						.compressionFactories(
								new ArrayList<>(BuiltinCompressions.VALUES))
						.build();
				client.setUserInteraction(
						new JGitUserInteraction(credentialsProvider));
				client.setUserAuthFactories(getUserAuthFactories());
				client.setKeyPairProvider(defaultKeysProvider);
				JGitSshClient jgitClient = (JGitSshClient) client;
				jgitClient.setKeyCache(getKeyCache());
				jgitClient.setCredentialsProvider(credentialsProvider);
				jgitClient.setProxyDatabase(proxies);
				String defaultAuths = getDefaultPreferredAuthentications();
				if (defaultAuths != null) {
					jgitClient.setAttribute(
							JGitSshClient.PREFERRED_AUTHENTICATIONS
							defaultAuths);
				}
				return client;
			});
			session.addCloseListener(s -> unregister(s));
			register(session);
			session.connect(Duration.ofMillis(tms));
			return session;
		} catch (Exception e) {
			unregister(session);
			throw new TransportException(uri
		}
	}

	@Override
	public void close() {
		closing.set(true);
		boolean cleanKeys = false;
		synchronized (this) {
			cleanKeys = sessions.isEmpty();
		}
		if (cleanKeys) {
			KeyCache cache = getKeyCache();
			if (cache != null) {
				cache.close();
			}
		}
	}

	private void register(SshdSession newSession) throws IOException {
		if (newSession == null) {
			return;
		}
		if (closing.get()) {
			throw new IOException(SshdText.get().sshClosingDown);
		}
		synchronized (this) {
			sessions.add(newSession);
		}
	}

	private void unregister(SshdSession oldSession) {
		boolean cleanKeys = false;
		synchronized (this) {
			sessions.remove(oldSession);
			cleanKeys = closing.get() && sessions.isEmpty();
		}
		if (cleanKeys) {
			KeyCache cache = getKeyCache();
			if (cache != null) {
				cache.close();
			}
		}
	}

	public void setHomeDirectory(@NonNull File homeDir) {
		if (homeDir.isAbsolute()) {
			homeDirectory = homeDir;
		} else {
			homeDirectory = homeDir.getAbsoluteFile();
		}
	}

	public File getHomeDirectory() {
		return homeDirectory;
	}

	public void setSshDirectory(@NonNull File sshDir) {
		if (sshDir.isAbsolute()) {
			sshDirectory = sshDir;
		} else {
			sshDirectory = sshDir.getAbsoluteFile();
		}
	}

	public File getSshDirectory() {
		return sshDirectory;
	}

	@NonNull
	private HostConfigEntryResolver getHostConfigEntryResolver(
			@NonNull File homeDir
		return defaultHostConfigEntryResolver.computeIfAbsent(
				new Tuple(new Object[] { homeDir
				t -> new JGitSshConfig(homeDir
						new File(sshDir
						getLocalUserName()));
	}

	@NonNull
	private ServerKeyVerifier getServerKeyVerifier(@NonNull File homeDir
			@NonNull File sshDir) {
		return defaultServerKeyVerifier.computeIfAbsent(
				new Tuple(new Object[] { homeDir
				t -> new OpenSshServerKeyVerifier(true
						getDefaultKnownHostsFiles(sshDir)));
	}

	@NonNull
	protected List<Path> getDefaultKnownHostsFiles(@NonNull File sshDir) {
		return Arrays.asList(sshDir.toPath().resolve(SshConstants.KNOWN_HOSTS)
				sshDir.toPath().resolve(SshConstants.KNOWN_HOSTS + '2'));
	}

	@NonNull
	protected Iterable<KeyPair> getDefaultKeys(@NonNull File sshDir) {
		List<Path> defaultIdentities = getDefaultIdentities(sshDir);
		return defaultKeys.computeIfAbsent(
				new Tuple(defaultIdentities.toArray(new Path[0]))
				t -> new CachingKeyPairProvider(defaultIdentities
						getKeyCache()));
	}

	private KeyPairProvider toKeyPairProvider(Iterable<KeyPair> keys) {
		if (keys instanceof KeyPairProvider) {
			return (KeyPairProvider) keys;
		}
		return () -> keys;
	}

	@NonNull
	protected List<Path> getDefaultIdentities(@NonNull File sshDir) {
		return Arrays
				.asList(SshConstants.DEFAULT_IDENTITIES).stream()
				.map(s -> new File(sshDir
				.collect(Collectors.toList());
	}

	protected final KeyCache getKeyCache() {
		return keyCache;
	}

	@NonNull
	protected KeyPasswordProvider createKeyPasswordProvider(
			CredentialsProvider provider) {
		return new IdentityPasswordProvider(provider);
	}

	@NonNull
	private FilePasswordProvider createFilePasswordProvider(
			KeyPasswordProvider provider) {
		return new PasswordProviderWrapper(provider);
	}

	@NonNull
	private List<NamedFactory<UserAuth>> getUserAuthFactories() {
		return Collections.unmodifiableList(
				Arrays.asList(GssApiWithMicAuthFactory.INSTANCE
						JGitPublicKeyAuthFactory.INSTANCE
						JGitPasswordAuthFactory.INSTANCE
						UserAuthKeyboardInteractiveFactory.INSTANCE));
	}

	protected String getDefaultPreferredAuthentications() {
		return null;
	}
}
