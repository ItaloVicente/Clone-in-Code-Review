package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import org.apache.sshd.client.auth.AbstractUserAuth;
import org.apache.sshd.client.auth.pubkey.PublicKeyIdentity;
import org.apache.sshd.client.auth.pubkey.UserAuthPublicKey;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.SshConstants;
import org.apache.sshd.common.config.keys.KeyUtils;
import org.apache.sshd.common.signature.Signature;
import org.apache.sshd.common.signature.SignatureFactoriesManager;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;

public class JGitPublicKeyAuthentication extends AbstractUserAuth
		implements SignatureFactoriesManager {

	private JGitPublicKeyIterator keys;

	private PublicKeyIdentity current;

	private List<NamedFactory<Signature>> factories;

	public JGitPublicKeyAuthentication(
			List<NamedFactory<Signature>> factories) {
		super(UserAuthPublicKey.NAME);
		this.factories = factories;
	}

	@Override
	public void init(ClientSession session
		super.init(session
		releaseKeys();
		keys = new JGitPublicKeyIterator(session
	}

	@Override
	protected boolean sendAuthDataRequest(ClientSession session
			throws Exception {
		if (keys == null || !keys.hasNext()) {
			return false;
		}
		current = keys.next();
		PublicKey key = current.getPublicKey();
		Buffer buffer = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		buffer.putString(session.getUsername());
		buffer.putString(service);
		buffer.putString(getName());
		buffer.putBoolean(false);
		buffer.putString(KeyUtils.getKeyType(key));
		buffer.putPublicKey(key);
		session.writePacket(buffer);
		return true;
	}

	@Override
	protected boolean processAuthDataRequest(ClientSession session
			String service
		String name = getName();
		int cmd = buffer.getUByte();
		if (cmd != SshConstants.SSH_MSG_USERAUTH_PK_OK) {
			throw new IllegalStateException(
							+ SshConstants.getCommandMessageName(cmd));
		}
		PublicKey key = current.getPublicKey();
		String algorithm = KeyUtils.getKeyType(key);
		String responseKeyType = buffer.getString();
		if (!responseKeyType.equals(algorithm)) {
			throw new InvalidKeySpecException(
							+ algorithm + "
		}
		PublicKey responseKey = buffer.getPublicKey();
		if (!KeyUtils.compareKeys(responseKey
					+ KeyUtils.getFingerPrint(key) + "
					+ KeyUtils.getFingerPrint(responseKey));
		}
		String username = session.getUsername();
		Buffer out = session
				.createBuffer(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		out.putString(username);
		out.putString(service);
		out.putString(name);
		out.putBoolean(true);
		out.putString(algorithm);
		out.putPublicKey(key);
		appendSignature(session
		session.writePacket(out);
		return true;
	}

	private void appendSignature(ClientSession session
			String name
			Buffer buffer) throws Exception {
		byte[] id = session.getSessionId();
		Buffer bs = new ByteArrayBuffer(id.length + username.length()
				+ service.length() + name.length() + algorithm.length()
				+ ByteArrayBuffer.DEFAULT_SIZE + Long.SIZE
		bs.putBytes(id);
		bs.putByte(SshConstants.SSH_MSG_USERAUTH_REQUEST);
		bs.putString(username);
		bs.putString(service);
		bs.putString(name);
		bs.putBoolean(true);
		bs.putString(algorithm);
		bs.putPublicKey(key);

		byte[] contents = bs.getCompactData();
		byte[] sig = current.sign(contents);

		bs.clear();
		bs.putString(algorithm);
		bs.putBytes(sig);
		buffer.putBytes(bs.array()
	}

	@Override
	public List<NamedFactory<Signature>> getSignatureFactories() {
		return factories;
	}

	@Override
	public void setSignatureFactories(List<NamedFactory<Signature>> factories) {
		this.factories = factories;
	}

	@Override
	public void destroy() {
		try {
			releaseKeys();
		} catch (IOException e) {
			throw new RuntimeException(
					"Failed to close agent: " + e.getMessage()
		} finally {
			super.destroy();
		}
	}

	private void releaseKeys() throws IOException {
		if (keys == null) {
			return;
		}
		try {
			keys.close();
		} finally {
			keys = null;
		}
	}

}
