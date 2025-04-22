package org.eclipse.jgit.internal.transport.sshd;

import static org.eclipse.jgit.internal.transport.sshd.CachingKeyPairProvider.getKeyId;

import java.security.KeyPair;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.sshd.client.auth.password.PasswordAuthenticationReporter;
import org.apache.sshd.client.auth.password.UserAuthPassword;
import org.apache.sshd.client.auth.pubkey.PublicKeyAuthenticationReporter;
import org.apache.sshd.client.auth.pubkey.UserAuthPublicKey;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.config.keys.KeyUtils;

public class AuthenticationLogger {

	private final List<String> messages = new ArrayList<>();


	private final PublicKeyAuthenticationReporter pubkeyLogger = new PublicKeyAuthenticationReporter() {

		private boolean hasAttempts;

		@Override
		public void signalAuthenticationAttempt(ClientSession session
				String service
				throws Exception {
			hasAttempts = true;
			String message;
			if (identity.getPrivate() == null) {
				message = MessageFormat.format(
						SshdText.get().authPubkeyAttemptAgent
						UserAuthPublicKey.NAME
						getKeyId(session
			} else {
				message = MessageFormat.format(
						SshdText.get().authPubkeyAttempt
						UserAuthPublicKey.NAME
						getKeyId(session
			}
			messages.add(message);
		}

		@Override
		public void signalAuthenticationExhausted(ClientSession session
				String service) throws Exception {
			String message;
			if (hasAttempts) {
				message = MessageFormat.format(
						SshdText.get().authPubkeyExhausted
						UserAuthPublicKey.NAME);
			} else {
				message = MessageFormat.format(SshdText.get().authPubkeyNoKeys
						UserAuthPublicKey.NAME);
			}
			messages.add(message);
			hasAttempts = false;
		}

		@Override
		public void signalAuthenticationFailure(ClientSession session
				String service
				List<String> serverMethods) throws Exception {
			String message;
			if (partial) {
				message = MessageFormat.format(
						SshdText.get().authPubkeyPartialSuccess
						UserAuthPublicKey.NAME
						getKeyId(session
			} else {
				message = MessageFormat.format(
						SshdText.get().authPubkeyFailure
						UserAuthPublicKey.NAME
						getKeyId(session
			}
			messages.add(message);
		}
	};

	private final PasswordAuthenticationReporter passwordLogger = new PasswordAuthenticationReporter() {

		private int attempts;

		@Override
		public void signalAuthenticationAttempt(ClientSession session
				String service
				String newPassword) throws Exception {
			attempts++;
			String message;
			if (modified) {
				message = MessageFormat.format(
						SshdText.get().authPasswordChangeAttempt
						UserAuthPassword.NAME
			} else {
				message = MessageFormat.format(
						SshdText.get().authPasswordAttempt
						UserAuthPassword.NAME
			}
			messages.add(message);
		}

		@Override
		public void signalAuthenticationExhausted(ClientSession session
				String service) throws Exception {
			String message;
			if (attempts > 0) {
				message = MessageFormat.format(
						SshdText.get().authPasswordExhausted
						UserAuthPassword.NAME);
			} else {
				message = MessageFormat.format(
						SshdText.get().authPasswordNotTried
						UserAuthPassword.NAME);
			}
			messages.add(message);
			attempts = 0;
		}

		@Override
		public void signalAuthenticationFailure(ClientSession session
				String service
				java.util.List<String> serverMethods) throws Exception {
			String message;
			if (partial) {
				message = MessageFormat.format(
						SshdText.get().authPasswordPartialSuccess
						UserAuthPassword.NAME
			} else {
				message = MessageFormat.format(
						SshdText.get().authPasswordFailure
						UserAuthPassword.NAME);
			}
			messages.add(message);
		}
	};

	private final GssApiWithMicAuthenticationReporter gssReporter = new GssApiWithMicAuthenticationReporter() {

		private boolean hasAttempts;

		@Override
		public void signalAuthenticationAttempt(ClientSession session
				String service
			hasAttempts = true;
			String message = MessageFormat.format(
					SshdText.get().authGssApiAttempt
					GssApiWithMicAuthFactory.NAME
			messages.add(message);
		}

		@Override
		public void signalAuthenticationExhausted(ClientSession session
				String service) {
			String message;
			if (hasAttempts) {
				message = MessageFormat.format(
						SshdText.get().authGssApiExhausted
						GssApiWithMicAuthFactory.NAME);
			} else {
				message = MessageFormat.format(
						SshdText.get().authGssApiNotTried
						GssApiWithMicAuthFactory.NAME);
			}
			messages.add(message);
			hasAttempts = false;
		}

		@Override
		public void signalAuthenticationFailure(ClientSession session
				String service
				java.util.List<String> serverMethods) {
			String message;
			if (partial) {
				message = MessageFormat.format(
						SshdText.get().authGssApiPartialSuccess
						GssApiWithMicAuthFactory.NAME
						serverMethods);
			} else {
				message = MessageFormat.format(
						SshdText.get().authGssApiFailure
						GssApiWithMicAuthFactory.NAME
			}
			messages.add(message);
		}
	};

	public AuthenticationLogger(ClientSession session) {
		session.setPublicKeyAuthenticationReporter(pubkeyLogger);
		session.setPasswordAuthenticationReporter(passwordLogger);
		session.setAttribute(
				GssApiWithMicAuthenticationReporter.GSS_AUTHENTICATION_REPORTER
				gssReporter);
	}

	public List<String> getLog() {
		return Collections.unmodifiableList(messages);
	}

	public void clear() {
		messages.clear();
	}
}
