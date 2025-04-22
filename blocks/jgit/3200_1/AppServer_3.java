	public void addSslConnector(final String ksPath
		assertNotYetSetUp();
		sslConnector = new SslSelectChannelConnector();
		sslConnector.setHost(connector.getHost());
		sslConnector.setPort(8443);
		sslConnector.setKeystore(ksPath);
		sslConnector.setPassword(ksPassword);
		sslConnector.setKeyPassword(ksPassword);
		sslConnector.setTruststore(ksPath);
		sslConnector.setTrustPassword(ksPassword);
		sslConnector.setWantClientAuth(false);
		sslConnector.setNeedClientAuth(true);

		server.addConnector(sslConnector);
	}

