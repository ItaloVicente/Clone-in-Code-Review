package org.eclipse.jgit.internal.transport.sshd;

import java.util.List;

import org.apache.sshd.client.auth.AbstractUserAuthFactory;
import org.apache.sshd.client.auth.UserAuth;
import org.apache.sshd.client.auth.pubkey.UserAuthPublicKeyFactory;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.common.signature.Signature;
import org.apache.sshd.common.signature.SignatureFactoriesManager;

public class JGitPublicKeyAuthFactory extends AbstractUserAuthFactory
		implements SignatureFactoriesManager {

	public static final JGitPublicKeyAuthFactory INSTANCE = new JGitPublicKeyAuthFactory();

	private JGitPublicKeyAuthFactory() {
		super(UserAuthPublicKeyFactory.NAME);
	}

	@Override
	public UserAuth create() {
		return new JGitPublicKeyAuthentication(getSignatureFactories());
	}

	@Override
	public List<NamedFactory<Signature>> getSignatureFactories() {
		return null;
	}

	@Override
	public void setSignatureFactories(List<NamedFactory<Signature>> factories) {
		throw new UnsupportedOperationException();
	}
}
