package org.eclipse.jgit.transport.sshd;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.sshd.client.config.hosts.HostConfigEntry;
import org.eclipse.jgit.transport.sshd.proxy.HttpClientConnector;
import org.eclipse.jgit.transport.sshd.proxy.Socks5ClientConnector;

public class DefaultProxyDatabase implements ProxyDatabase {

	@Override
	public ProxyData get(HostConfigEntry hostConfig
			InetSocketAddress remoteAddress) {
		try {
			List<Proxy> proxies = ProxySelector.getDefault()
					.select(new URI(Proxy.Type.SOCKS.name()
			ProxyData data = getData(proxies
			if (data == null) {
				proxies = ProxySelector.getDefault()
						.select(new URI(Proxy.Type.HTTP.name()
								null));
				data = getData(proxies
			}
			return data;
		} catch (URISyntaxException e) {
			return null;
		}
	}

	private ProxyData getData(List<Proxy> proxies
			InetSocketAddress remoteAddress) {
		Proxy proxy = proxies.stream().filter(p -> type == p.type()).findFirst()
				.orElse(null);
		if (proxy == null) {
			return null;
		}
		SocketAddress address = proxy.address();
		if (!(address instanceof InetSocketAddress)) {
			return null;
		}
		InetSocketAddress proxyAddress = (InetSocketAddress) address;
		switch (type) {
		case HTTP:
			return new ProxyData(proxyAddress
					new HttpClientConnector(proxyAddress
		case SOCKS:
			return new ProxyData(proxyAddress
					new Socks5ClientConnector(proxyAddress
		default:
			return null;
		}
	}
}
