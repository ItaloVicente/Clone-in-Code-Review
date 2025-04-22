	private static final Config.SectionParser<HttpConfig> HTTP_KEY = new SectionParser<HttpConfig>() {
		@Override
		public HttpConfig parse(final Config cfg) {
			return new HttpConfig(cfg);
		}
	};

