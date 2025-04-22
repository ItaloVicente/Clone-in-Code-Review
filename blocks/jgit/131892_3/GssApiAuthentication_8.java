package org.eclipse.jgit.internal.transport.sshd.auth;

import java.net.Authenticator;
import java.net.Authenticator.RequestorType;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.concurrent.CancellationException;

import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.transport.SshConstants;

public abstract class BasicAuthentication<ParameterType
		extends AbstractAuthenticationHandler<ParameterType

	protected String user;

	protected byte[] password;

	public BasicAuthentication(InetSocketAddress proxy
			char[] initialPassword) {
		super(proxy);
		this.user = initialUser;
		this.password = convert(initialPassword);
	}

	private byte[] convert(char[] pass) {
		if (pass == null) {
			return new byte[0];
		}
		ByteBuffer bytes = StandardCharsets.UTF_8.encode(CharBuffer.wrap(pass));
		byte[] pwd = new byte[bytes.remaining()];
		bytes.get(pwd);
		if (bytes.hasArray()) {
			Arrays.fill(bytes.array()
		}
		Arrays.fill(pass
		return pwd;
	}

	protected void clearPassword() {
		if (password != null) {
			Arrays.fill(password
		}
		password = new byte[0];
	}

	@Override
	public final void close() {
		clearPassword();
		done = true;
	}

	@Override
	public final void start() throws Exception {
		if (user != null && !user.isEmpty()
				|| password != null && password.length > 0) {
			return;
		}
		askCredentials();
	}

	@Override
	public void process() throws Exception {
		askCredentials();
	}

	protected void askCredentials() {
		clearPassword();
		PasswordAuthentication auth = AccessController
				.doPrivileged(new PrivilegedAction<PasswordAuthentication>() {

					@Override
					public PasswordAuthentication run() {
						return Authenticator.requestPasswordAuthentication(
								proxy.getHostString()
								proxy.getPort()
								SshdText.get().proxyPasswordPrompt
								null
					}
				});
		if (auth == null) {
			throw new CancellationException(
					SshdText.get().authenticationCanceled);
		}
		user = auth.getUserName();
		password = convert(auth.getPassword());
	}
}
