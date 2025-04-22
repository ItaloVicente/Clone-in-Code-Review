package org.eclipse.jgit.internal.transport.sshd.proxy;

import java.util.concurrent.Callable;

import org.apache.sshd.client.session.ClientProxyConnector;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.util.Readable;

public interface StatefulProxyConnector extends ClientProxyConnector {

	static final String TIMEOUT_PROPERTY = StatefulProxyConnector.class

	void messageReceived(IoSession session

	void runWhenDone(Callable<Void> startSsh) throws Exception;
}
