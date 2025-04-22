		if (client == null)
			client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
			isUsingProxy = true;
			InetSocketAddress adr = (InetSocketAddress) proxy.address();
			params.setParameter(ConnRoutePNames.DEFAULT_PROXY,
					new HttpHost(adr.getHostName(), adr.getPort()));
