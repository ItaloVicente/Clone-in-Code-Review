package org.eclipse.jgit.internal.transport.sshd;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.sshd.client.auth.AbstractUserAuth;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.SshConstants;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;
import org.ietf.jgss.GSSContext;
import org.ietf.jgss.GSSException;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;

public class GssApiWithMicAuthentication extends AbstractUserAuth {

	private static final byte SSH_MSG_USERAUTH_GSSAPI_RESPONSE = SshConstants.SSH_MSG_USERAUTH_INFO_REQUEST;

	private static final byte SSH_MSG_USERAUTH_GSSAPI_TOKEN = SshConstants.SSH_MSG_USERAUTH_INFO_RESPONSE;

	private enum ProtocolState {
		STARTED
	}

	private Collection<Oid> mechanisms;

	private Iterator<Oid> nextMechanism;

	private Oid currentMechanism;

	private ProtocolState state;

	private GSSContext context;

	public GssApiWithMicAuthentication() {
		super(GssApiWithMicAuthFactory.NAME);
	}

	@Override
	protected boolean sendAuthDataRequest(ClientSession session
			throws Exception {
		if (mechanisms == null) {
			mechanisms = GssApiMechanisms.getSupportedMechanisms();
			nextMechanism = mechanisms.iterator();
		}
		if (context != null) {
			close(false);
		}
		if (!nextMechanism.hasNext()) {
			return false;
		}
		state = ProtocolState.STARTED;
		currentMechanism = nextMechanism.next();
		while (GssApiMechanisms.SPNEGO.equals(currentMechanism)) {
			if (!nextMechanism.hasNext()) {
				return false;
			}
			currentMechanism = nextMechanism.next();
		}
		try {
			String hostName = getHostName(session);
			context = GssApiMechanisms.createContext(currentMechanism
					hostName);
			context.requestMutualAuth(true);
			context.requestConf(true);
			context.requestInteg(true);
			context.requestCredDeleg(true);
			context.requestAnonymity(false);
		} catch (GSSException | NullPointerException e) {
			close(true);
			if (log.isDebugEnabled()) {
				log.debug(format(SshdText.get().gssapiInitFailure
						currentMechanism.toString()));
			}
			currentMechanism = null;
			state = ProtocolState.FAILED;
			return false;
		}
		Buffer buffer = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		buffer.putString(session.getUsername());
		buffer.putString(service);
		buffer.putString(getName());
		buffer.putInt(1);
		buffer.putBytes(currentMechanism.getDER());
		session.writePacket(buffer);
		return true;
	}

	@Override
	protected boolean processAuthDataRequest(ClientSession session
			String service
		int command = in.getUByte();
		if (context == null) {
			return false;
		}
		try {
			switch (command) {
			case SSH_MSG_USERAUTH_GSSAPI_RESPONSE: {
				if (state != ProtocolState.STARTED) {
					return unexpectedMessage(command);
				}
				Oid mechanism = new Oid(in.getBytes());
				if (!currentMechanism.equals(mechanism)) {
					return false;
				}
				replyToken(session
				return true;
			}
			case SSH_MSG_USERAUTH_GSSAPI_TOKEN: {
				if (context.isEstablished() || state != ProtocolState.TOKENS) {
					return unexpectedMessage(command);
				}
				replyToken(session
				return true;
			}
			default:
				return unexpectedMessage(command);
			}
		} catch (GSSException e) {
			log.warn(format(SshdText.get().gssapiFailure
					currentMechanism.toString())
			state = ProtocolState.FAILED;
			return false;
		}
	}

	@Override
	public void destroy() {
		try {
			close(false);
		} finally {
			super.destroy();
		}
	}

	private void close(boolean silent) {
		try {
			if (context != null) {
				context.dispose();
				context = null;
			}
		} catch (GSSException e) {
			if (!silent) {
				log.warn(SshdText.get().gssapiFailure
			}
		}
	}

	private void sendToken(ClientSession session
			throws IOException
		state = ProtocolState.TOKENS;
		byte[] token = context.initSecContext(receivedToken
				receivedToken.length);
		if (token != null) {
			Buffer buffer = session.createBuffer(SSH_MSG_USERAUTH_GSSAPI_TOKEN);
			buffer.putBytes(token);
			session.writePacket(buffer);
		}
	}

	private void sendMic(ClientSession session
			throws IOException
		state = ProtocolState.MIC_SENT;
		Buffer micBuffer = new ByteArrayBuffer();
		micBuffer.putBytes(session.getSessionId());
		micBuffer.putByte(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		micBuffer.putString(session.getUsername());
		micBuffer.putString(service);
		micBuffer.putString(getName());
		byte[] micBytes = micBuffer.getCompactData();
		byte[] mic = context.getMIC(micBytes
				new MessageProp(0
		Buffer buffer = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_GSSAPI_MIC);
		buffer.putBytes(mic);
		session.writePacket(buffer);
	}

	private void replyToken(ClientSession session
			throws IOException
		sendToken(session
		if (context.isEstablished()) {
			sendMic(session
		}
	}

	private String getHostName(ClientSession session) {
		SocketAddress remote = session.getConnectAddress();
		if (remote instanceof InetSocketAddress) {
			InetAddress address = GssApiMechanisms
					.resolve((InetSocketAddress) remote);
			if (address != null) {
				return address.getCanonicalHostName();
			}
		}
		if (session instanceof JGitClientSession) {
			String hostName = ((JGitClientSession) session).getHostConfigEntry()
					.getHostName();
			try {
				hostName = InetAddress.getByName(hostName)
						.getCanonicalHostName();
			} catch (UnknownHostException e) {
			}
			return hostName;
		}
		throw new IllegalStateException(
	}

	private boolean unexpectedMessage(int command) {
		log.warn(format(SshdText.get().gssapiUnexpectedMessage
				Integer.toString(command)));
		return false;
	}

}
