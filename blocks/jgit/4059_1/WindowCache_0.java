		reconfigureWithSystemProperties(new WindowCacheConfig());
	}

	public static void reconfigureWithSystemProperties(WindowCacheConfig c) {
		c.setDeltaBaseCacheLimit(getSystemPropertyValueOrDefaultInteger(
				"org.eclipse.jgit.config.deltaBaseCacheLimit"
				c.getDeltaBaseCacheLimit()));

		c.setPackedGitLimit(getSystemPropertyValueOrDefaultLong(
				"org.eclipse.jgit.config.packagedGitLimit"
				c.getPackedGitLimit()));

		c.setPackedGitOpenFiles(getSystemPropertyValueOrDefaultInteger(
				"org.eclipse.jgit.config.packedGitOpenFiles"
				c.getPackedGitOpenFiles()));

		c.setPackedGitWindowSize(getSystemPropertyValueOrDefaultInteger(
				"org.eclipse.jgit.config.packedGitWindowSize"
				c.getPackedGitWindowSize()));

		c.setStreamFileThreshold(getSystemPropertyValueOrDefaultInteger(
				"org.eclipse.jgit.config.streamFileThreshold"
				c.getStreamFileThreshold()));

		reconfigure(c);
	}

	private static int getSystemPropertyValueOrDefaultInteger(String key
			int defaultValue) {
		final String propertyString = System.getProperty(key);
		int propertyValue = defaultValue;
		if (propertyString != null) {
			try {
				propertyValue = Integer.valueOf(propertyString).intValue();
			} catch (NumberFormatException e) {
			}
		}
		return propertyValue;
	}

	private static long getSystemPropertyValueOrDefaultLong(String key
			long defaultValue) {
		final String propertyString = System.getProperty(key);
		long propertyValue = defaultValue;
		if (propertyString != null) {
			try {
				propertyValue = Long.valueOf(propertyString).longValue();
			} catch (NumberFormatException e) {
			}
		}
		return propertyValue;
