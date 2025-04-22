		if (client == null) {
			HttpClientBuilder builder = HttpClientBuilder.create();
			if (proxy != null && !Proxy.NO_PROXY.equals(proxy)) {
				isUsingProxy = true;
				HttpHost proxyHost = getProxyHost(proxy);
				builder.setProxy(proxyHost);
				try {
					URIish proxyURIish = new URIish(new URL(
							proxyHost.getSchemeName()
							proxyHost.getPort()
					Credentials credentials = getBasicCredentials(credProv
							proxyURIish);
					if (credentials != null) {
						AuthScope proxyScope = new AuthScope(proxyHost);
						BasicCredentialsProvider proxyCredentialsProvider = new BasicCredentialsProvider();
						proxyCredentialsProvider.setCredentials(proxyScope
								credentials);
						builder.setDefaultCredentialsProvider(
										proxyCredentialsProvider);
					}
				} catch (MalformedURLException e) {
				}
			}
			client = builder.build();
