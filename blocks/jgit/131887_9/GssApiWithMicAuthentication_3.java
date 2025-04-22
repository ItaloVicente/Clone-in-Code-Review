package org.eclipse.jgit.internal.transport.sshd;

import org.apache.sshd.client.auth.AbstractUserAuthFactory;
import org.apache.sshd.client.auth.UserAuth;

public class GssApiWithMicAuthFactory extends AbstractUserAuthFactory {


	public static final GssApiWithMicAuthFactory INSTANCE = new GssApiWithMicAuthFactory();

	private GssApiWithMicAuthFactory() {
		super(NAME);
	}

	@Override
	public UserAuth create() {
		return new GssApiWithMicAuthentication();
	}

}
