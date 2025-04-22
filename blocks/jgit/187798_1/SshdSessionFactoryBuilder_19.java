	public SshdSessionFactoryBuilder setConnectorFactory(
			ConnectorFactory factory) {
		this.state.connectorFactory = factory;
		this.state.connectorFactorySet = true;
		return this;
	}

	public SshdSessionFactoryBuilder withDefaultConnectorFactory() {
		this.state.connectorFactory = null;
		this.state.connectorFactorySet = false;
		return this;
	}

