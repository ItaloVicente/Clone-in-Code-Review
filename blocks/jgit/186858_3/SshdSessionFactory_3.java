package org.eclipse.jgit.internal.transport.sshd.agent;

import java.io.IOException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.text.MessageFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.sshd.agent.SshAgent;
import org.apache.sshd.agent.SshAgentConstants;
import org.apache.sshd.common.SshException;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.session.SessionContext;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.BufferException;
import org.apache.sshd.common.util.buffer.BufferUtils;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.eclipse.jgit.transport.sshd.agent.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SshAgentClient implements SshAgent {

	private static final Logger LOG = LoggerFactory
			.getLogger(SshAgentClient.class);

	private static final int MAX_NUMBER_OF_KEYS = 2048;

	private final AtomicBoolean closed = new AtomicBoolean();

	private final Connector connector;

	public SshAgentClient(Connector connector) {
		this.connector = connector;
	}

	private boolean open(boolean debugging) throws IOException {
		if (closed.get()) {
			if (debugging) {
			}
			return false;
		}
		boolean connected = connector != null && connector.connect();
		if (!connected) {
			if (debugging) {
			}
		}
		return connected;
	}

	@Override
	public void close() throws IOException {
		if (!closed.getAndSet(true) && connector != null) {
			connector.close();
		}
	}

	@Override
	public Iterable<? extends Map.Entry<PublicKey
			throws IOException {
		boolean debugging = LOG.isDebugEnabled();
		if (!open(debugging)) {
			return Collections.emptyList();
		}
		if (debugging) {
		}
		try {
			Buffer reply = rpc(
					SshAgentConstants.SSH2_AGENTC_REQUEST_IDENTITIES);
			byte cmd = reply.getByte();
			if (cmd != SshAgentConstants.SSH2_AGENT_IDENTITIES_ANSWER) {
				throw new SshException(MessageFormat.format(
						SshdText.get().sshAgentReplyUnexpected
						SshAgentConstants.getCommandMessageName(cmd)));
			}
			int numberOfKeys = reply.getInt();
			if (numberOfKeys < 0 || numberOfKeys > MAX_NUMBER_OF_KEYS) {
				throw new SshException(MessageFormat.format(
						SshdText.get().sshAgentWrongNumberOfKeys
						Integer.toString(numberOfKeys)));
			}
			if (numberOfKeys == 0) {
				if (debugging) {
				}
				return Collections.emptyList();
			}
			if (debugging) {
				LOG.debug("Got {} key(s) from the SSH agent"
						Integer.toString(numberOfKeys));
			}
			boolean tracing = LOG.isTraceEnabled();
			List<Map.Entry<PublicKey
					numberOfKeys);
			for (int i = 0; i < numberOfKeys; i++) {
				PublicKey key = reply.getPublicKey();
				String comment = reply.getString();
				if (tracing) {
					LOG.trace("Got SSH agent {} key: {} {}"
							KeyUtils.getKeyType(key)
							KeyUtils.getFingerPrint(key)
				}
				keys.add(new AbstractMap.SimpleImmutableEntry<>(key
			}
			return keys;
		} catch (BufferException e) {
			throw new SshException(SshdText.get().sshAgentShortReadBuffer
		}
	}

	@Override
	public Map.Entry<String
			String algorithm
		boolean debugging = LOG.isDebugEnabled();
		String keyType = KeyUtils.getKeyType(key);
		String signatureAlgorithm;
		if (algorithm != null) {
			if (!KeyUtils.getCanonicalKeyType(algorithm).equals(keyType)) {
				throw new IllegalArgumentException(MessageFormat.format(
						SshdText.get().invalidSignatureAlgorithm
						keyType));
			}
			signatureAlgorithm = algorithm;
		} else {
			signatureAlgorithm = keyType;
		}
		if (!open(debugging)) {
			return null;
		}
		int flags = 0;
		switch (signatureAlgorithm) {
		case KeyUtils.RSA_SHA512_KEY_TYPE_ALIAS:
		case KeyUtils.RSA_SHA512_CERT_TYPE_ALIAS:
			flags = 4;
			break;
		case KeyUtils.RSA_SHA256_KEY_TYPE_ALIAS:
		case KeyUtils.RSA_SHA256_CERT_TYPE_ALIAS:
			flags = 2;
			break;
		default:
			break;
		}
		ByteArrayBuffer msg = new ByteArrayBuffer();
		msg.putInt(0);
		msg.putByte(SshAgentConstants.SSH2_AGENTC_SIGN_REQUEST);
		msg.putPublicKey(key);
		msg.putBytes(data);
		msg.putInt(flags);
		if (debugging) {
			LOG.debug(
					"sign({}): signing request to SSH agent for {} key
					session
					Integer.toString(flags));
		}
		Buffer reply = rpc(SshAgentConstants.SSH2_AGENTC_SIGN_REQUEST
				msg.getCompactData());
		byte cmd = reply.getByte();
		if (cmd != SshAgentConstants.SSH2_AGENT_SIGN_RESPONSE) {
			throw new SshException(
					MessageFormat.format(SshdText.get().sshAgentReplyUnexpected
							SshAgentConstants.getCommandMessageName(cmd)));
		}
		try {
			Buffer signatureReply = new ByteArrayBuffer(reply.getBytes());
			String actualAlgorithm = signatureReply.getString();
			byte[] signature = signatureReply.getBytes();
			if (LOG.isTraceEnabled()) {
				LOG.trace(
						"sign({}): signature reply from SSH agent for {} key: {} signature={}"
						session
						BufferUtils.toHex(':'

			} else if (LOG.isDebugEnabled()) {
				LOG.debug(
						"sign({}): signature reply from SSH agent for {} key
						session
			}
			return new AbstractMap.SimpleImmutableEntry<>(actualAlgorithm
					signature);
		} catch (BufferException e) {
			throw new SshException(SshdText.get().sshAgentShortReadBuffer
		}
	}

	private Buffer rpc(byte command
		return new ByteArrayBuffer(connector.rpc(command
	}

	private Buffer rpc(byte command) throws IOException {
		return new ByteArrayBuffer(connector.rpc(command));
	}

	@Override
	public boolean isOpen() {
		return !closed.get();
	}

	@Override
	public void addIdentity(KeyPair key
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeIdentity(PublicKey key) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeAllIdentities() throws IOException {
		throw new UnsupportedOperationException();
	}
}
