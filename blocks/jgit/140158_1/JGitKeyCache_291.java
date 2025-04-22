package org.eclipse.jgit.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CancellationException;

import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;

public class IdentityPasswordProvider implements KeyPasswordProvider {

	private CredentialsProvider provider;

	private int attempts = 1;

	protected static class State {

		private int count = 0;

		private char[] password;

		public int getCount() {
			return count;
		}

		public int incCount() {
			return ++count;
		}

		public void setPassword(char[] password) {
			if (this.password != null) {
				Arrays.fill(this.password
			}
			if (password != null) {
				this.password = password.clone();
			} else {
				this.password = null;
			}
		}

		public char[] getPassword() {
			return password;
		}
	}

	private final Map<URIish

	public IdentityPasswordProvider(CredentialsProvider provider) {
		this.provider = provider;
	}

	@Override
	public void setAttempts(int numberOfPasswordPrompts) {
		if (numberOfPasswordPrompts <= 0) {
			throw new IllegalArgumentException(
		}
		attempts = numberOfPasswordPrompts;
	}

	@Override
	public int getAttempts() {
		return Math.max(1
	}

	@Override
	public char[] getPassphrase(URIish uri
		return getPassword(uri
				current.computeIfAbsent(uri
	}

	protected char[] getPassword(URIish uri
			throws IOException {
		state.setPassword(null);
		state.incCount();
		String message = state.count == 1 ? SshdText.get().keyEncryptedMsg
				: SshdText.get().keyEncryptedRetry;
		char[] pass = getPassword(uri
		state.setPassword(pass);
		return pass;
	}

	private char[] getPassword(URIish uri
		if (provider == null) {
			return null;
		}
		List<CredentialItem> items = new ArrayList<>(2);
		items.add(new CredentialItem.InformationalMessage(
				format(message
		CredentialItem.Password password = new CredentialItem.Password(
				SshdText.get().keyEncryptedPrompt);
		items.add(password);
		try {
			provider.get(uri
			char[] pass = password.getValue();
			if (pass == null) {
				throw new CancellationException(
						SshdText.get().authenticationCanceled);
			}
			return pass.clone();
		} finally {
			password.clear();
		}
	}

	protected boolean keyLoaded(URIish uri
			State state
			throws IOException
		if (err == null) {
		} else if (err instanceof GeneralSecurityException) {
			throw new InvalidKeyException(
					format(SshdText.get().identityFileCannotDecrypt
		} else {
			if (state == null || password == null
					|| state.getCount() >= attempts) {
				return false;
			}
			return true;
		}
	}

	@Override
	public boolean keyLoaded(URIish uri
			throws IOException
		State state = null;
		boolean retry = false;
		try {
			state = current.get(uri);
			retry = keyLoaded(uri
					state == null ? null : state.getPassword()
		} finally {
			if (state != null) {
				state.setPassword(null);
			}
			if (!retry) {
				current.remove(uri);
			}
		}
		return retry;
	}
}
