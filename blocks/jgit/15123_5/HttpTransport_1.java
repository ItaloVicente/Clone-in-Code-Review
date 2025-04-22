	protected static HttpConnectionFactory connectionFactory = new JDKHttpConnectionFactory();

	public static HttpConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public static void setConnectionFactory(HttpConnectionFactory cf) {
		connectionFactory = cf;
	}

