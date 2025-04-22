	public default double getDouble(String key, double defaultValue) {
		try {
			return getDouble(key);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

