		if (client == null) {
			HttpClientBuilder clientBuilder = HttpClients.custom();
			RequestConfig.Builder configBuilder = RequestConfig.custom();
			if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
				isUsingProxy = true;
				InetSocketAddress adr = (InetSocketAddress) proxy.address();
				clientBuilder.setProxy(
						new HttpHost(adr.getHostName()
			}
			if (timeout != null) {
				configBuilder.setConnectTimeout(timeout.intValue());
			}
			if (readTimeout != null) {
				configBuilder.setSocketTimeout(readTimeout.intValue());
			}
			if (followRedirects != null) {
				configBuilder
						.setRedirectsEnabled(followRedirects.booleanValue());
			}
			if (hostnameverifier != null) {
				SSLConnectionSocketFactory sslConnectionFactory = new SSLConnectionSocketFactory(
						getSSLContext()
				clientBuilder.setSSLSocketFactory(sslConnectionFactory);
				Registry<ConnectionSocketFactory> registry = RegistryBuilder
						.<ConnectionSocketFactory> create()
						.register("https"
				clientBuilder.setConnectionManager(
						new BasicHttpClientConnectionManager(registry));
			}
			clientBuilder.setDefaultRequestConfig(configBuilder.build());
			client = clientBuilder.build();
