package org.eclipse.jgit.internal.transport.sshd;

import java.util.concurrent.CancellationException;

import org.apache.sshd.client.ClientAuthenticationManager;
import org.apache.sshd.client.auth.keyboard.UserInteraction;
import org.apache.sshd.client.auth.password.UserAuthPassword;
import org.apache.sshd.client.session.ClientSession;

public class JGitPasswordAuthentication extends UserAuthPassword {

	private int maxAttempts;

	private int attempts;

	@Override
	public void init(ClientSession session
		super.init(session
		maxAttempts = Math.max(1
				session.getIntProperty(
						ClientAuthenticationManager.PASSWORD_PROMPTS
						ClientAuthenticationManager.DEFAULT_PASSWORD_PROMPTS));
		attempts = 0;
	}

	@Override
	protected boolean sendAuthDataRequest(ClientSession session
			throws Exception {
		if (++attempts > maxAttempts) {
			return false;
		}
		UserInteraction interaction = session.getUserInteraction();
		if (!interaction.isInteractionAllowed(session)) {
			return false;
		}
		String password = getPassword(session
		if (password == null) {
			throw new CancellationException();
		}
		sendPassword(null
		return true;
	}

	private String getPassword(ClientSession session
			UserInteraction interaction) {
		String[] results = interaction.interactive(session
				new String[] { SshdText.get().passwordPrompt }
				new boolean[] { false });
		return (results == null || results.length == 0) ? null : results[0];
	}
}
