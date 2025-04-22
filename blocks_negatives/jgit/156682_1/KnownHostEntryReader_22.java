/*
 * Copyright (C) 2018, Thomas Wolf <thomas.wolf@paranor.ch> and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package org.eclipse.jgit.internal.transport.sshd;

import static org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.flag;
import static org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.positive;

import java.io.File;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.Map;
import java.util.TreeMap;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.common.AttributeRepository;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile;
import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.HostEntry;
import org.eclipse.jgit.transport.SshConstants;

/**
 * A {@link HostConfigEntryResolver} adapted specifically for JGit.
 * <p>
 * We use our own config file parser and entry resolution since the default
 * {@link org.apache.sshd.client.config.hosts.ConfigFileHostEntryResolver
 * ConfigFileHostEntryResolver} has a number of problems:
 * </p>
 * <ul>
 * <li>It does case-insensitive pattern matching. Matching in OpenSsh is
 * case-sensitive! Compare also bug 531118.</li>
 * <li>It only merges values from the global items (before the first "Host"
 * line) into the host entries. Otherwise it selects the most specific match.
 * OpenSsh processes <em>all</em> entries in the order they appear in the file
 * and whenever one matches, it updates values as appropriate.</li>
 * <li>We have to ensure that ~ replacement uses the same HOME directory as
 * JGit. Compare bug bug 526175.</li>
 * </ul>
 * Therefore, this re-uses the parsing and caching from
 * {@link OpenSshConfigFile}.
 *
 */
public class JGitSshConfig implements HostConfigEntryResolver {

	private final OpenSshConfigFile configFile;

	private final String localUserName;

	/**
	 * Creates a new {@link OpenSshConfigFile} that will read the config from
	 * file {@code config} use the given file {@code home} as "home" directory.
	 *
	 * @param home
	 *            user's home directory for the purpose of ~ replacement
	 * @param config
	 *            file to load; may be {@code null} if no ssh config file
	 *            handling is desired
	 * @param localUserName
	 *            user name of the current user on the local host OS
	 */
	public JGitSshConfig(@NonNull File home, File config,
			@NonNull String localUserName) {
		this.localUserName = localUserName;
		configFile = config == null ?  null : new OpenSshConfigFile(home, config, localUserName);
	}

	@Override
	public HostConfigEntry resolveEffectiveHost(String host, int port,
			SocketAddress localAddress, String username,
			AttributeRepository attributes) throws IOException {
		HostEntry entry = configFile == null ? new HostEntry() : configFile.lookup(host, port, username);
		JGitHostConfigEntry config = new JGitHostConfigEntry();
		Map<String, String> allOptions = new TreeMap<>(
				String.CASE_INSENSITIVE_ORDER);
		allOptions.putAll(entry.getOptions());
		entry.getMultiValuedOptions().entrySet().stream()
				.forEach(e -> allOptions.put(e.getKey(),
						String.join(",, e.getValue()))); //$NON-NLS-1$
-		config.setProperties(allOptions);
-		// The following is an extension from JGitHostConfigEntry
-		config.setMultiValuedOptions(entry.getMultiValuedOptions());
-		// Also make sure the underlying properties are set
-		String hostName = entry.getValue(SshConstants.HOST_NAME);
-		if (hostName == null || hostName.isEmpty()) {
-			hostName = host;
-		}
-		config.setHostName(hostName);
-		config.setProperty(SshConstants.HOST_NAME, hostName);
-		config.setHost(SshdSocketAddress.isIPv6Address(hostName) ? " : hostName); //$NON-NLS-1$
-		String user = username != null && !username.isEmpty() ? username
-				: entry.getValue(SshConstants.USER);
-		if (user == null || user.isEmpty()) {
-			user = localUserName;
-		}
-		config.setUsername(user);
-		config.setProperty(SshConstants.USER, user);
-		int p = port >= 0 ? port : positive(entry.getValue(SshConstants.PORT));
-		config.setPort(p >= 0 ? p : SshConstants.SSH_DEFAULT_PORT);
-		config.setProperty(SshConstants.PORT,
-				Integer.toString(config.getPort()));
-		config.setIdentities(entry.getValues(SshConstants.IDENTITY_FILE));
-		config.setIdentitiesOnly(
-				flag(entry.getValue(SshConstants.IDENTITIES_ONLY)));
-		return config;
-	}
-
-}
diff --git a/org.eclipse.jgit.ssh.apache/src/org/eclipse/jgit/internal/transport/sshd/JGitUserInteraction.java b/org.eclipse.jgit.ssh.apache/src/org/eclipse/jgit/internal/transport/sshd/JGitUserInteraction.java
deleted file mode 100644
index c51a75bc6f..0000000000
--- a/org.eclipse.jgit.ssh.apache/src/org/eclipse/jgit/internal/transport/sshd/JGitUserInteraction.java
+++ /dev/null
@@ -1,186 +0,0 @@
-/*
- * Copyright (C) 2018, Thomas Wolf <thomas.wolf@paranor.ch> and others
- *
- * This program and the accompanying materials are made available under the
- * terms of the Eclipse Distribution License v. 1.0 which is available at
- * https://www.eclipse.org/org/documents/edl-v10.php.
- *
- * SPDX-License-Identifier: BSD-3-Clause
- */
-package org.eclipse.jgit.internal.transport.sshd;
-
-import java.net.InetSocketAddress;
-import java.util.ArrayList;
-import java.util.List;
-import java.util.Map;
-import java.util.concurrent.ConcurrentHashMap;
-
-import org.apache.sshd.client.auth.keyboard.UserInteraction;
-import org.apache.sshd.client.session.ClientSession;
-import org.apache.sshd.common.session.Session;
-import org.apache.sshd.common.session.SessionListener;
-import org.eclipse.jgit.transport.CredentialItem;
-import org.eclipse.jgit.transport.CredentialsProvider;
-import org.eclipse.jgit.transport.SshConstants;
-import org.eclipse.jgit.transport.URIish;
-
-/**
- * A {@link UserInteraction} callback implementation based on a
- * {@link CredentialsProvider}.
- */
-public class JGitUserInteraction implements UserInteraction {
-
-	private final CredentialsProvider provider;
-
-	/**
-	 * We need to reset the JGit credentials provider if we have repeated
-	 * attempts.
-	 */
-	private final Map<Session, SessionListener> ongoing = new ConcurrentHashMap<>();
-
-	/**
-	 * Creates a new {@link JGitUserInteraction} for interactive password input
-	 * based on the given {@link CredentialsProvider}.
-	 *
-	 * @param provider
-	 *            to use
-	 */
-	public JGitUserInteraction(CredentialsProvider provider) {
-		this.provider = provider;
-	}
-
-	@Override
-	public boolean isInteractionAllowed(ClientSession session) {
-		return provider != null && provider.isInteractive();
-	}
-
-	@Override
-	public String[] interactive(ClientSession session, String name,
-			String instruction, String lang, String[] prompt, boolean[] echo) {
-		// This is keyboard-interactive or password authentication
-		List<CredentialItem> items = new ArrayList<>();
-		int numberOfHiddenInputs = 0;
-		for (int i = 0; i < prompt.length; i++) {
-			boolean hidden = i < echo.length && !echo[i];
-			if (hidden) {
-				numberOfHiddenInputs++;
-			}
-		}
-		// RFC 4256 (SSH_MSG_USERAUTH_INFO_REQUEST) says: The language tag is
		if (name != null && !name.isEmpty()) {
			items.add(new CredentialItem.InformationalMessage(name));
		}
		if (instruction != null && !instruction.isEmpty()) {
			items.add(new CredentialItem.InformationalMessage(instruction));
		}
		for (int i = 0; i < prompt.length; i++) {
			boolean hidden = i < echo.length && !echo[i];
			if (hidden && numberOfHiddenInputs == 1) {
				items.add(new CredentialItem.Password());
			} else {
				items.add(new CredentialItem.StringType(prompt[i], hidden));
			}
		}
		if (items.isEmpty()) {
		}
		URIish uri = toURI(session.getUsername(),
				(InetSocketAddress) session.getConnectAddress());
		if (numberOfHiddenInputs > 0) {
			SessionListener listener = ongoing.get(session);
			if (listener != null) {
				provider.reset(uri);
			} else {
				listener = new SessionAuthMarker(ongoing);
				ongoing.put(session, listener);
				session.addSessionListener(listener);
			}
		}
		if (provider.get(uri, items)) {
			return items.stream().map(i -> {
				if (i instanceof CredentialItem.Password) {
					return new String(((CredentialItem.Password) i).getValue());
				} else if (i instanceof CredentialItem.StringType) {
					return ((CredentialItem.StringType) i).getValue();
				}
				return null;
			}).filter(s -> s != null).toArray(String[]::new);
		}
		return null;
	}

	@Override
	public String getUpdatedPassword(ClientSession session, String prompt,
			String lang) {
		return null;
	}

	/**
	 * Creates a {@link URIish} from the given remote address and user name.
	 *
	 * @param userName
	 *            for the uri
	 * @param remote
	 *            address of the remote host
	 * @return the uri, with {@link SshConstants#SSH_SCHEME} as scheme
	 */
	public static URIish toURI(String userName, InetSocketAddress remote) {
		String host = remote.getHostString();
		int port = remote.getPort();
				.setUser(userName);
	}

	/**
	 * A {@link SessionListener} that removes itself from the session when
	 * authentication is done or the session is closed.
	 */
	private static class SessionAuthMarker implements SessionListener {

		private final Map<Session, SessionListener> registered;

		public SessionAuthMarker(Map<Session, SessionListener> registered) {
			this.registered = registered;
		}

		@Override
		public void sessionEvent(Session session, SessionListener.Event event) {
			if (event == SessionListener.Event.Authenticated) {
				session.removeSessionListener(this);
				registered.remove(session, this);
			}
		}

		@Override
		public void sessionClosed(Session session) {
			session.removeSessionListener(this);
			registered.remove(session, this);
		}
	}
}
