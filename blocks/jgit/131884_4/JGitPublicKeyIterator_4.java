package org.eclipse.jgit.internal.transport.sshd;

import java.util.List;

import org.apache.sshd.client.auth.pubkey.UserAuthPublicKey;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.signature.Signature;

public class JGitPublicKeyAuthentication extends UserAuthPublicKey {

	private ClientSession clientSession;

	private String serviceName;

	public JGitPublicKeyAuthentication(
			List<NamedFactory<Signature>> factories) {
		super(factories);
	}

	@Override
	public void init(ClientSession session
		clientSession = session;
		serviceName = service;
		releaseKeys();
		keys = new JGitPublicKeyIterator(session
	}

	@Override
	public ClientSession getClientSession() {
		return clientSession;
	}

	@Override
	public ClientSession getSession() {
		return clientSession;
	}

	@Override
	public String getService() {
		return serviceName;
	}
}
