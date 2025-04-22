		if (ssl) {
			assertNotNull(sslConnector);
			return sslConnector.getLocalPort();
		} else {
			return connector.getLocalPort();
		}
