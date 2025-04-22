	public ServletContextHandler authClientCert(ServletContextHandler ctx) {
		assertNotNull(sslConnector);
		assertNotYetSetUp();
		auth(ctx
		return ctx;
	}

