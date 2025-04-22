		if (client == null)
			client = new DefaultHttpClient();
		HttpParams params = client.getParams();
		if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
			isUsingProxy = true;
			InetSocketAddress adr = (InetSocketAddress) proxy.address();
			params.setParameter(ConnRoutePNames.DEFAULT_PROXY,
					new HttpHost(adr.getHostName(), adr.getPort()));
		}
		if (timeout != null)
			params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
					timeout.intValue());
		if (readTimeout != null)
			params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT,
					readTimeout.intValue());
		if (followRedirects != null)
			params.setBooleanParameter(ClientPNames.HANDLE_REDIRECTS,
					followRedirects.booleanValue());
		if (hostnameverifier != null) {
			SSLSocketFactory sf;
			sf = new SSLSocketFactory(getSSLContext(), hostnameverifier);
			Scheme https = new Scheme("https", 443, sf); //$NON-NLS-1$
			client.getConnectionManager().getSchemeRegistry().register(https);
