	public default int getInt(String key, int defaultValue) {
		try {
			return getInt(key);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

