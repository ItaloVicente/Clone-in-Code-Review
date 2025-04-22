		String host;
		if (ssl) {
			assertNotNull(sslConnector);
			host = sslConnector.getHost();
		} else {
			host = connector.getHost();
		}
