package org.eclipse.jgit.internal.transport.sshd;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.sshd.client.auth.keyboard.UserInteraction;
import org.apache.sshd.client.session.ClientSession;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.SshConstants;
import org.eclipse.jgit.transport.URIish;

public class JGitUserInteraction implements UserInteraction {

	private final CredentialsProvider provider;

	public JGitUserInteraction(CredentialsProvider provider) {
		this.provider = provider;
	}

	@Override
	public boolean isInteractionAllowed(ClientSession session) {
		return provider.isInteractive();
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
				(InetSocketAddress) session.getIoSession().getRemoteAddress());
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
}
