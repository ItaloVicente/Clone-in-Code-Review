	public default float getFloat(String key, float defaultValue) {
		try {
			return getFloat(key);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

