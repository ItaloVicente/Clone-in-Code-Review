package org.eclipse.jgit.internal.transport.sshd.auth;

import static java.text.MessageFormat.format;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.eclipse.jgit.internal.transport.sshd.GssApiMechanisms;
import org.eclipse.jgit.internal.transport.sshd.SshdText;
import org.ietf.jgss.GSSContext;

public abstract class GssApiAuthentication<ParameterType
		extends AbstractAuthenticationHandler<ParameterType

	private GSSContext context;

	protected byte[] token;

	public GssApiAuthentication(InetSocketAddress proxy) {
		super(proxy);
	}

	@Override
	public void close() {
		GssApiMechanisms.closeContextSilently(context);
		context = null;
		done = true;
	}

	@Override
	public final void start() throws Exception {
		try {
			context = createContext();
			context.requestMutualAuth(true);
			context.requestConf(false);
			context.requestInteg(false);
			byte[] empty = new byte[0];
			token = context.initSecContext(empty
		} catch (Exception e) {
			close();
			throw e;
		}
	}

	@Override
	public final void process() throws Exception {
		if (context == null) {
			throw new IOException(
					format(SshdText.get().proxyCannotAuthenticate
		}
		try {
			byte[] received = extractToken(params);
			token = context.initSecContext(received
			checkDone();
		} catch (Exception e) {
			close();
			throw e;
		}
	}

	private void checkDone() throws Exception {
		done = context.isEstablished();
		if (done) {
			context.dispose();
			context = null;
		}
	}

	protected abstract GSSContext createContext() throws Exception;

	protected abstract byte[] extractToken(ParameterType input)
			throws Exception;
}
