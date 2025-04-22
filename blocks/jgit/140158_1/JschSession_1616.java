
package org.eclipse.jgit.transport;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.FS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ConfigRepository;
import com.jcraft.jsch.ConfigRepository.Config;
import com.jcraft.jsch.HostKey;
import com.jcraft.jsch.HostKeyRepository;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public abstract class JschConfigSessionFactory extends SshSessionFactory {

	private static final Logger LOG = LoggerFactory
			.getLogger(JschConfigSessionFactory.class);

	private final Map<String

	private JSch defaultJSch;

	private OpenSshConfig config;

	@Override
	public synchronized RemoteSession getSession(URIish uri
			CredentialsProvider credentialsProvider
			throws TransportException {

		String user = uri.getUser();
		final String pass = uri.getPass();
		String host = uri.getHost();
		int port = uri.getPort();

		try {
			if (config == null)
				config = OpenSshConfig.get(fs);

			final OpenSshConfig.Host hc = config.lookup(host);
			if (port <= 0)
				port = hc.getPort();
			if (user == null)
				user = hc.getUser();

			Session session = createSession(credentialsProvider
					pass

			int retries = 0;
			while (!session.isConnected()) {
				try {
					retries++;
					session.connect(tms);
				} catch (JSchException e) {
					session.disconnect();
					session = null;
					knownHosts(getJSch(hc

					if (isAuthenticationCanceled(e)) {
						throw e;
					} else if (isAuthenticationFailed(e)
							&& credentialsProvider != null) {
						if (retries < 3) {
							credentialsProvider.reset(uri);
							session = createSession(credentialsProvider
									user
						} else
							throw e;
					} else if (retries >= hc.getConnectionAttempts()) {
						throw e;
					} else {
						try {
							Thread.sleep(1000);
							session = createSession(credentialsProvider
									user
						} catch (InterruptedException e1) {
							throw new TransportException(
									JGitText.get().transportSSHRetryInterrupt
									e1);
						}
					}
				}
			}

			return new JschSession(session

		} catch (JSchException je) {
			final Throwable c = je.getCause();
			if (c instanceof UnknownHostException) {
				throw new TransportException(uri
						je);
			}
			if (c instanceof ConnectException) {
				throw new TransportException(uri
			}
			throw new TransportException(uri
		}

	}

	private static boolean isAuthenticationFailed(JSchException e) {
	}

	private static boolean isAuthenticationCanceled(JSchException e) {
	}

	Session createSession(CredentialsProvider credentialsProvider
			FS fs
			final OpenSshConfig.Host hc) throws JSchException {
		final Session session = createSession(hc
		setUserName(session
		if (port > 0 && port != session.getPort()) {
			session.setPort(port);
		}
		session.setConfig("MaxAuthTries"
		if (pass != null)
			session.setPassword(pass);
		final String strictHostKeyCheckingPolicy = hc
				.getStrictHostKeyChecking();
		if (strictHostKeyCheckingPolicy != null)
			session.setConfig("StrictHostKeyChecking"
					strictHostKeyCheckingPolicy);
		final String pauth = hc.getPreferredAuthentications();
		if (pauth != null)
			session.setConfig("PreferredAuthentications"
		if (credentialsProvider != null
				&& (!hc.isBatchMode() || !credentialsProvider.isInteractive())) {
			session.setUserInfo(new CredentialsProviderUserInfo(session
					credentialsProvider));
		}
		safeConfig(session
			setPreferredKeyTypesOrder(session);
		}
		configure(hc
		return session;
	}

	private void safeConfig(Session session
		copyConfigValueToSession(session
		copyConfigValueToSession(session
		copyConfigValueToSession(session
	}

	private static void setPreferredKeyTypesOrder(Session session) {
		HostKeyRepository hkr = session.getHostKeyRepository();
		List<String> known = Stream.of(hkr.getHostKey(hostName(session)
				.map(HostKey::getType)
				.collect(toList());

		if (!known.isEmpty()) {
			String current = session.getConfig(serverHostKey);
			if (current == null) {
				session.setConfig(serverHostKey
				Integer.valueOf(s.getPort()));
	}

	private void copyConfigValueToSession(Session session
			String from
		String value = cfg.getValue(from);
		if (value != null) {
			session.setConfig(to
		}
	}

	private void setUserName(Session session
		if (userName == null || userName.isEmpty()
				|| userName.equals(session.getUserName())) {
			return;
		}
		try {
			Class<?>[] parameterTypes = { String.class };
			Method method = Session.class.getDeclaredMethod("setUserName"
					parameterTypes);
			method.setAccessible(true);
			method.invoke(session
		} catch (NullPointerException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.error(MessageFormat.format(JGitText.get().sshUserNameError
					userName
		}
	}

	protected Session createSession(final OpenSshConfig.Host hc
			final String user
			throws JSchException {
		return getJSch(hc
	}

	protected void configureJSch(JSch jsch) {
	}

	protected abstract void configure(OpenSshConfig.Host hc

	protected JSch getJSch(OpenSshConfig.Host hc
		if (defaultJSch == null) {
			defaultJSch = createDefaultJSch(fs);
			if (defaultJSch.getConfigRepository() == null) {
				defaultJSch.setConfigRepository(
						new JschBugFixingConfigRepository(config));
			}
			for (Object name : defaultJSch.getIdentityNames())
				byIdentityFile.put((String) name
		}

		final File identityFile = hc.getIdentityFile();
		if (identityFile == null)
			return defaultJSch;

		final String identityKey = identityFile.getAbsolutePath();
		JSch jsch = byIdentityFile.get(identityKey);
		if (jsch == null) {
			jsch = new JSch();
			configureJSch(jsch);
			if (jsch.getConfigRepository() == null) {
				jsch.setConfigRepository(defaultJSch.getConfigRepository());
			}
			jsch.setHostKeyRepository(defaultJSch.getHostKeyRepository());
			jsch.addIdentity(identityKey);
			byIdentityFile.put(identityKey
		}
		return jsch;
	}

	protected JSch createDefaultJSch(FS fs) throws JSchException {
		final JSch jsch = new JSch();
		JSch.setConfig("ssh-rsa"
		JSch.setConfig("ssh-dss"
		configureJSch(jsch);
		knownHosts(jsch
		identities(jsch
		return jsch;
	}

	private static void knownHosts(JSch sch
		final File home = fs.userHome();
		if (home == null)
			return;
		final File known_hosts = new File(new File(home
		try (FileInputStream in = new FileInputStream(known_hosts)) {
			sch.setKnownHosts(in);
		} catch (FileNotFoundException none) {
		} catch (IOException err) {
		}
	}

	private static void identities(JSch sch
		final File home = fs.userHome();
		if (home == null)
			return;
		final File sshdir = new File(home
		if (sshdir.isDirectory()) {
			loadIdentity(sch
			loadIdentity(sch
			loadIdentity(sch
		}
	}

	private static void loadIdentity(JSch sch
		if (priv.isFile()) {
			try {
				sch.addIdentity(priv.getAbsolutePath());
			} catch (JSchException e) {
			}
		}
	}

	private static class JschBugFixingConfigRepository
			implements ConfigRepository {

		private final ConfigRepository base;

		public JschBugFixingConfigRepository(ConfigRepository base) {
			this.base = base;
		}

		@Override
		public Config getConfig(String host) {
			return new JschBugFixingConfig(base.getConfig(host));
		}

		private static class JschBugFixingConfig implements Config {

			private static final String[] NO_IDENTITIES = {};

			private final Config real;

			public JschBugFixingConfig(Config delegate) {
				real = delegate;
			}

			@Override
			public String getHostname() {
				return real.getHostname();
			}

			@Override
			public String getUser() {
				return real.getUser();
			}

			@Override
			public int getPort() {
				return real.getPort();
			}

			@Override
			public String getValue(String key) {
				String k = key.toUpperCase(Locale.ROOT);
					return null;
				}
				String result = real.getValue(key);
				if (result != null) {
						try {
							int timeout = Integer.parseInt(result);
							result = Long.toString(
									TimeUnit.SECONDS.toMillis(timeout));
						} catch (NumberFormatException e) {
						}
					}
				}
				return result;
			}

			@Override
			public String[] getValues(String key) {
				String k = key.toUpperCase(Locale.ROOT);
					return NO_IDENTITIES;
				}
				return real.getValues(key);
			}
		}
	}

	void setConfig(OpenSshConfig config) {
		this.config = config;
	}
}
