package org.eclipse.jgit.internal.transport.sshd;

import java.io.IOException;

import org.apache.sshd.client.auth.pubkey.UserAuthPublicKey;
import org.apache.sshd.client.auth.pubkey.UserAuthPublicKeyFactory;
import org.apache.sshd.client.session.ClientSession;

public class JGitPublicKeyAuthFactory extends UserAuthPublicKeyFactory {

	public static final JGitPublicKeyAuthFactory FACTORY = new JGitPublicKeyAuthFactory();

	private JGitPublicKeyAuthFactory() {
		super();
	}

	@Override
	public UserAuthPublicKey createUserAuth(ClientSession session)
			throws IOException {
		return new JGitPublicKeyAuthentication(getSignatureFactories());
	}
}
