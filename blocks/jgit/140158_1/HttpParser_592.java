package org.eclipse.jgit.internal.transport.sshd.proxy;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.util.Readable;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.internal.transport.sshd.GssApiMechanisms;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.internal.transport.sshd.auth.AuthenticationHandler;
import org.eclipse.jgit.internal.transport.sshd.auth.BasicAuthentication;
import org.eclipse.jgit.internal.transport.sshd.auth.GssApiAuthentication;
import org.eclipse.jgit.util.Base64;
import org.ietf.jgss.GSSContext;

public class HttpClientConnector extends AbstractClientProxyConnector {



	private HttpAuthenticationHandler basic;

	private HttpAuthenticationHandler negotiate;

	private List<HttpAuthenticationHandler> availableAuthentications;

	private Iterator<HttpAuthenticationHandler> clientAuthentications;

	private HttpAuthenticationHandler authenticator;

	private boolean ongoing;

	public HttpClientConnector(@NonNull InetSocketAddress proxyAddress
			@NonNull InetSocketAddress remoteAddress) {
		this(proxyAddress
	}

	public HttpClientConnector(@NonNull InetSocketAddress proxyAddress
			@NonNull InetSocketAddress remoteAddress
			char[] proxyPassword) {
		super(proxyAddress
		basic = new HttpBasicAuthentication();
		negotiate = new NegotiateAuthentication();
		availableAuthentications = new ArrayList<>(2);
		availableAuthentications.add(negotiate);
		availableAuthentications.add(basic);
		clientAuthentications = availableAuthentications.iterator();
	}

	private void close() {
		HttpAuthenticationHandler current = authenticator;
		authenticator = null;
		if (current != null) {
			current.close();
		}
	}

	@Override
	public void sendClientProxyMetadata(ClientSession sshSession)
			throws Exception {
		init(sshSession);
		IoSession session = sshSession.getIoSession();
		session.addCloseFutureListener(f -> close());
		StringBuilder msg = connect();
		if (proxyUser != null && !proxyUser.isEmpty()
				|| proxyPassword != null && proxyPassword.length > 0) {
			authenticator = basic;
			basic.setParams(null);
			basic.start();
			msg = authenticate(msg
			clearPassword();
			proxyUser = null;
		}
		ongoing = true;
		try {
			send(msg
		} catch (Exception e) {
			ongoing = false;
			throw e;
		}
	}

	private void send(StringBuilder msg
		byte[] data = eol(msg).toString().getBytes(US_ASCII);
		Buffer buffer = new ByteArrayBuffer(data.length
		buffer.putRawBytes(data);
		session.writePacket(buffer).verify(getTimeout());
	}

	private StringBuilder connect() {
		StringBuilder msg = new StringBuilder();
		return msg.append(format(
				"CONNECT {0}:{1} HTTP/1.1\r\nProxy-Connection: keep-alive\r\nConnection: keep-alive\r\nHost: {0}:{1}\r\n"
				remoteAddress.getHostString()
				Integer.toString(remoteAddress.getPort())));
	}

	private StringBuilder authenticate(StringBuilder msg
		msg.append(HTTP_HEADER_PROXY_AUTHORIZATION).append(' ').append(token);
		return eol(msg);
	}

	private StringBuilder eol(StringBuilder msg) {
		return msg.append('\r').append('\n');
	}

	@Override
	public void messageReceived(IoSession session
			throws Exception {
		try {
			int length = buffer.available();
			byte[] data = new byte[length];
			buffer.getRawBytes(data
			String[] reply = new String(data
			handleMessage(session
		} catch (Exception e) {
			if (authenticator != null) {
				authenticator.close();
				authenticator = null;
			}
			ongoing = false;
			try {
				setDone(false);
			} catch (Exception inner) {
				e.addSuppressed(inner);
			}
			throw e;
		}
	}

	private void handleMessage(IoSession session
			throws Exception {
		if (reply.isEmpty() || reply.get(0).isEmpty()) {
			throw new IOException(
					format(SshdText.get().proxyHttpUnexpectedReply
							proxyAddress
		}
		try {
			StatusLine status = HttpParser.parseStatusLine(reply.get(0));
			if (!ongoing) {
				throw new IOException(format(
						SshdText.get().proxyHttpUnexpectedReply
						Integer.toString(status.getResultCode())
						status.getReason()));
			}
			switch (status.getResultCode()) {
			case HttpURLConnection.HTTP_OK:
				if (authenticator != null) {
					authenticator.close();
				}
				authenticator = null;
				ongoing = false;
				setDone(true);
				break;
			case HttpURLConnection.HTTP_PROXY_AUTH:
				List<AuthenticationChallenge> challenges = HttpParser
						.getAuthenticationHeaders(reply
								HTTP_HEADER_PROXY_AUTHENTICATION);
				authenticator = selectProtocol(challenges
				if (authenticator == null) {
					throw new IOException(
							format(SshdText.get().proxyCannotAuthenticate
									proxyAddress));
				}
				String token = authenticator.getToken();
				if (token == null) {
					throw new IOException(
							format(SshdText.get().proxyCannotAuthenticate
									proxyAddress));
				}
				send(authenticate(connect()
				break;
			default:
				throw new IOException(format(SshdText.get().proxyHttpFailure
						proxyAddress
						status.getReason()));
			}
		} catch (HttpParser.ParseException e) {
			throw new IOException(
					format(SshdText.get().proxyHttpUnexpectedReply
					proxyAddress
		}
	}

	private HttpAuthenticationHandler selectProtocol(
			List<AuthenticationChallenge> challenges
			HttpAuthenticationHandler current) throws Exception {
		if (current != null && !current.isDone()) {
			AuthenticationChallenge challenge = getByName(challenges
					current.getName());
			if (challenge != null) {
				current.setParams(challenge);
				current.process();
				return current;
			}
		}
		if (current != null) {
			current.close();
		}
		while (clientAuthentications.hasNext()) {
			HttpAuthenticationHandler next = clientAuthentications.next();
			if (!next.isDone()) {
				AuthenticationChallenge challenge = getByName(challenges
						next.getName());
				if (challenge != null) {
					next.setParams(challenge);
					next.start();
					return next;
				}
			}
		}
		return null;
	}

	private AuthenticationChallenge getByName(
			List<AuthenticationChallenge> challenges
			String name) {
		return challenges.stream()
				.filter(c -> c.getMechanism().equalsIgnoreCase(name))
				.findFirst().orElse(null);
	}

	private interface HttpAuthenticationHandler
			extends AuthenticationHandler<AuthenticationChallenge

		public String getName();
	}

	private class HttpBasicAuthentication
			extends BasicAuthentication<AuthenticationChallenge
			implements HttpAuthenticationHandler {

		private boolean asked;

		public HttpBasicAuthentication() {
			super(proxyAddress
		}

		@Override
		public String getName() {
		}

		@Override
		protected void askCredentials() {
			if (asked) {
				throw new IllegalStateException(
			}
			asked = true;
			super.askCredentials();
			done = true;
		}

		@Override
		public String getToken() throws Exception {
			if (user.indexOf(':') >= 0) {
				throw new IOException(format(
						SshdText.get().proxyHttpInvalidUserName
			}
			byte[] rawUser = user.getBytes(UTF_8);
			byte[] toEncode = new byte[rawUser.length + 1 + password.length];
			System.arraycopy(rawUser
			toEncode[rawUser.length] = ':';
			System.arraycopy(password
					password.length);
			Arrays.fill(password
			String result = Base64.encodeBytes(toEncode);
			Arrays.fill(toEncode
			return getName() + ' ' + result;
		}

	}

	private class NegotiateAuthentication
			extends GssApiAuthentication<AuthenticationChallenge
			implements HttpAuthenticationHandler {

		public NegotiateAuthentication() {
			super(proxyAddress);
		}

		@Override
		public String getName() {
		}

		@Override
		public String getToken() throws Exception {
			return getName() + ' ' + Base64.encodeBytes(token);
		}

		@Override
		protected GSSContext createContext() throws Exception {
			return GssApiMechanisms.createContext(GssApiMechanisms.SPNEGO
					GssApiMechanisms.getCanonicalName(proxyAddress));
		}

		@Override
		protected byte[] extractToken(AuthenticationChallenge input)
				throws Exception {
			String received = input.getToken();
			if (received == null) {
				return new byte[0];
			}
			return Base64.decode(received);
		}

	}
}
