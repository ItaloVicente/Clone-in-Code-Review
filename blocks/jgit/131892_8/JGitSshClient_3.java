		setAttribute(ORIGINAL_REMOTE_ADDRESS
		ProxyData proxy = getProxyData(hostConfig
		if (proxy != null) {
			setClientProxyConnector(proxy.getConnector());
			address = proxy.getAddress();
		}
