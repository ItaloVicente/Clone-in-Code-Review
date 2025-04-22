package org.eclipse.jgit.internal.transport.sshd;

import static org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.flag;
import static org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.positive;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.apache.sshd.client.config.hosts.HostConfigEntryResolver;
import org.apache.sshd.common.util.net.SshdSocketAddress;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile;
import org.eclipse.jgit.internal.transport.ssh.OpenSshConfigFile.HostEntry;
import org.eclipse.jgit.transport.SshConstants;

public class JGitSshConfig implements HostConfigEntryResolver {

	private OpenSshConfigFile configFile;

	public JGitSshConfig(@NonNull File home
			@NonNull String localUserName) {
		configFile = new OpenSshConfigFile(home
	}

	@Override
	public HostConfigEntry resolveEffectiveHost(String host
			String username) throws IOException {
		HostEntry entry = configFile.lookup(host
		JGitHostConfigEntry config = new JGitHostConfigEntry();
		Map<String
				String.CASE_INSENSITIVE_ORDER);
		allOptions.putAll(entry.getOptions());
		entry.getMultiValuedOptions().entrySet().stream()
				.forEach(e -> allOptions.put(e.getKey()
						String.join("
package org.eclipse.jgit.internal.transport.sshd;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.sshd.client.auth.keyboard.UserInteraction;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.session.Session;
import org.apache.sshd.common.session.SessionListener;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.transport.URIish;

public class JGitUserInteraction implements UserInteraction {

	private final CredentialsProvider provider;

	private final Map<Session

	public JGitUserInteraction(CredentialsProvider provider) {
		this.provider = provider;
	}

	@Override
	public boolean isInteractionAllowed(ClientSession session) {
		return provider != null && provider.isInteractive();
	}

	@Override
	public String[] interactive(ClientSession session
			String instruction
		List<CredentialItem> items = new ArrayList<>();
		int numberOfHiddenInputs = 0;
		for (int i = 0; i < prompt.length; i++) {
			boolean hidden = i < echo.length && !echo[i];
			if (hidden) {
				numberOfHiddenInputs++;
			}
		}
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
				items.add(new CredentialItem.StringType(prompt[i]
			}
		}
		if (items.isEmpty()) {
		}
		URIish uri = toURI(session.getUsername()
				(InetSocketAddress) session.getConnectAddress());
		if (numberOfHiddenInputs > 0) {
			SessionListener listener = ongoing.get(session);
			if (listener != null) {
				provider.reset(uri);
			} else {
				listener = new SessionAuthMarker(ongoing);
				ongoing.put(session
				session.addSessionListener(listener);
			}
		}
		if (provider.get(uri
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
	public String getUpdatedPassword(ClientSession session
			String lang) {
		return null;
	}

	public static URIish toURI(String userName
		String host = remote.getHostString();
		int port = remote.getPort();
				.setUser(userName);
	}

	private static class SessionAuthMarker implements SessionListener {

		private final Map<Session

		public SessionAuthMarker(Map<Session
			this.registered = registered;
		}

		@Override
		public void sessionEvent(Session session
			if (event == SessionListener.Event.Authenticated) {
				session.removeSessionListener(this);
				registered.remove(session
			}
		}

		@Override
		public void sessionClosed(Session session) {
			session.removeSessionListener(this);
			registered.remove(session
		}
	}
}
