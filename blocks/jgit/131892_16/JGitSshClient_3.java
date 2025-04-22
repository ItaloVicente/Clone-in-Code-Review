		setAttribute(ORIGINAL_REMOTE_ADDRESS
		ProxyData proxy = getProxyData(hostConfig
		if (proxy != null) {
			address = configureProxy(proxy
			proxy.clearPassword();
		}
