package org.eclipse.jgit.internal.transport.sshd.auth;

import java.net.InetSocketAddress;

public abstract class AbstractAuthenticationHandler<ParameterType
		implements AuthenticationHandler<ParameterType

	protected InetSocketAddress proxy;

	protected ParameterType params;

	protected boolean done;

	public AbstractAuthenticationHandler(InetSocketAddress proxy) {
		this.proxy = proxy;
	}

	@Override
	public final void setParams(ParameterType input) {
		params = input;
	}

	@Override
	public final boolean isDone() {
		return done;
	}

}
