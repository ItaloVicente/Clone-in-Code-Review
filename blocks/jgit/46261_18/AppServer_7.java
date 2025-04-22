		if (sslPort >= 0) {
			SslContextFactory sslContextFactory = createTestSslContextFactory(
					hostName);
			secureConfig = new HttpConfiguration(config);
			secureConnector = new ServerConnector(server
					new SslConnectionFactory(sslContextFactory
							HttpVersion.HTTP_1_1.asString())
					new HttpConnectionFactory(secureConfig));
			secureConnector.setPort(sslPort);
			secureConnector.setHost(ip);
		} else {
			secureConfig = null;
			secureConnector = null;
		}

