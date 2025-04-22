	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ new JDKHttpConnectionFactory() },
				{ new HttpClientConnectionFactory() } });
	}

