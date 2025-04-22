package org.eclipse.jgit.transport.sshd;

import java.net.InetSocketAddress;
import java.util.Objects;

import org.apache.sshd.client.session.ClientProxyConnector;
import org.eclipse.jgit.annotations.NonNull;

public class ProxyData {

	private ClientProxyConnector connector;

	private InetSocketAddress proxyAddress;

	public ProxyData(InetSocketAddress proxy
		this.proxyAddress = proxy;
		this.connector = connector;
	}

	@NonNull
	public ClientProxyConnector getConnector() {
		return connector;
	}

	@NonNull
	public InetSocketAddress getAddress() {
		return proxyAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj != null && this.getClass().equals(obj.getClass())) {
			ProxyData other = (ProxyData) obj;
			return Objects.equals(proxyAddress
					&& Objects.equals(connector
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(proxyAddress
	}

}
