	public default long getLong(String key, long defaultValue) {
		try {
			return getLong(key);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

