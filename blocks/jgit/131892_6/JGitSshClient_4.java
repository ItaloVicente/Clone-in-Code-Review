	private ProxyData getProxyData(HostConfigEntry hostConfig
			InetSocketAddress remoteAddress) {
		ProxyDatabase factory = getProxyDatabase();
		return factory == null ? null : factory.get(hostConfig
	}

