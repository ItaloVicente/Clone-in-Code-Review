	static ConnectorFactory getDefault() {
		return ConnectorFactoryProvider.getDefaultFactory();
	}

	static void setDefault(ConnectorFactory factory) {
		ConnectorFactoryProvider.setDefaultFactory(factory);
	}

