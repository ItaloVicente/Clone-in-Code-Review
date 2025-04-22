	private ProxyData getProxyData(HostConfigEntry hostConfig
			InetSocketAddress remoteAddress) {
		ProxyDataFactory factory = getProxyDatabase();
		return factory == null ? null : factory.get(hostConfig
	}

	private InetSocketAddress configureProxy(ProxyData proxyData
			InetSocketAddress remoteAddress) {
		Proxy proxy = proxyData.getProxy();
		if (proxy.type() == Proxy.Type.DIRECT
				|| !(proxy.address() instanceof InetSocketAddress)) {
			return remoteAddress;
		}
		InetSocketAddress address = (InetSocketAddress) proxy.address();
		switch (proxy.type()) {
		case HTTP:
			setClientProxyConnector(
					new HttpClientConnector(address
							proxyData.getUser()
			return address;
		case SOCKS:
			setClientProxyConnector(
					new Socks5ClientConnector(address
							proxyData.getUser()
			return address;
		default:
			return remoteAddress;
		}
	}

