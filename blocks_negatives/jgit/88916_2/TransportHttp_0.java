	private static class HttpConfig {
		final int postBuffer;

		final boolean sslVerify;

		HttpConfig(final Config rc) {
			postBuffer = rc.getInt("http", "postbuffer", 1 * 1024 * 1024); //$NON-NLS-1$  //$NON-NLS-2$
			sslVerify = rc.getBoolean("http", "sslVerify", true); //$NON-NLS-1$ //$NON-NLS-2$
		}

		HttpConfig() {
			this(new Config());
		}
	}

