		return INSTANCE;
	}

	public static void setDefaultFactory(ConnectorFactory factory) {
		INSTANCE = factory == null ? loadDefaultFactory() : factory;
	}

	private ConnectorFactoryProvider() {
