	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ new JDKHttpConnectionFactory() }
				{ new HttpClientConnectionFactory() } });
	}

	public DumbClientDumbServerTest(HttpConnectionFactory cf) {
		HttpTransport.setConnectionFactory(cf);
	}

