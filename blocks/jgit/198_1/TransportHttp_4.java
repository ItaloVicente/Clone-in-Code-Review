	private static final Config.SectionParser<HttpConfig> HTTP_KEY = new SectionParser<HttpConfig>() {
		public HttpConfig parse(final Config cfg) {
			return new HttpConfig(cfg);
		}
	};

	private static class HttpConfig {
		final int postBuffer;

		HttpConfig(final Config rc) {
			postBuffer = rc.getInt("http"
		}
	}

