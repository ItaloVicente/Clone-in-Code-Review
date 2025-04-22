	/**
	 * Config values for http.followRedirect
	 */
	private static enum HttpRedirectMode implements Config.ConfigEnum {

		/** Always follow redirects (up to the http.maxRedirects limit). */
		TRUE("true"), //$NON-NLS-1$
		/**
		 * Only follow redirects on the initial GET request. This is the
		 * default.
		 */
		INITIAL("initial"), //$NON-NLS-1$
		/** Never follow redirects. */

		private final String configValue;

		private HttpRedirectMode(String configValue) {
			this.configValue = configValue;
		}

		@Override
		public String toConfigValue() {
			return configValue;
		}

		@Override
		public boolean matchConfigValue(String s) {
			return configValue.equals(s);
		}
	}

	private static class HttpConfig {
		final int postBuffer;

		final boolean sslVerify;

		final HttpRedirectMode followRedirects;

		final int maxRedirects;

		HttpConfig(final Config rc) {
			postBuffer = rc.getInt("http", "postbuffer", 1 * 1024 * 1024); //$NON-NLS-1$  //$NON-NLS-2$
			sslVerify = rc.getBoolean("http", "sslVerify", true); //$NON-NLS-1$ //$NON-NLS-2$
			followRedirects = rc.getEnum(HttpRedirectMode.values(), "http", //$NON-NLS-1$
					null, "followRedirects", HttpRedirectMode.INITIAL); //$NON-NLS-1$
			int redirectLimit = rc.getInt("http", "maxRedirects", //$NON-NLS-1$ //$NON-NLS-2$
					MAX_REDIRECTS);
			if (redirectLimit < 0) {
				redirectLimit = MAX_REDIRECTS;
			}
			maxRedirects = redirectLimit;
		}

		HttpConfig() {
			this(new Config());
		}
	}

