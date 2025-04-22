package org.eclipse.jgit.internal.transport.sshd;

import org.apache.sshd.client.auth.AbstractUserAuthFactory;
import org.apache.sshd.client.auth.UserAuth;
import org.apache.sshd.client.auth.password.UserAuthPasswordFactory;

public class JGitPasswordAuthFactory extends AbstractUserAuthFactory {

	public static final JGitPasswordAuthFactory INSTANCE = new JGitPasswordAuthFactory();

	private JGitPasswordAuthFactory() {
		super(UserAuthPasswordFactory.NAME);
	}

	@Override
	public UserAuth create() {
		return new JGitPasswordAuthentication();
	}
}
