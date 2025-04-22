	private static ObjectModeCache objectModeCache;


	private static int DEFAULT_CACHE_MAXSIZE = 10000000;

	static {
		try {
			String maxSizeProperty = System
					.getProperty(
					MAX_SIZE_PROPERTY);
			if (maxSizeProperty != null) {
				objectModeCache = new ObjectModeCache(
						Integer.parseInt(maxSizeProperty));
			} else {
				objectModeCache = ObjectModeCache.NOOP_CACHE;
			}
		} catch (NumberFormatException ex) {
			objectModeCache = new ObjectModeCache(DEFAULT_CACHE_MAXSIZE);
		}
	}

