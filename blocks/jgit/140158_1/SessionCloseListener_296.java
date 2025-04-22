package org.eclipse.jgit.transport.sshd;

import java.net.InetSocketAddress;

public interface ProxyDataFactory {

	ProxyData get(InetSocketAddress remoteAddress);
}
