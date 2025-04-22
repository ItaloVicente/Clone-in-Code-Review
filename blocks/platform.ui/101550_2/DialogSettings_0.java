	@Override
	public double getDouble(String key, double defaultValue) {
		String setting = items.get(key);
		if(setting != null) {
			try {
				return Double.valueOf(setting).doubleValue();
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	@Override
	public float getFloat(String key, float defaultValue) {
		String setting = items.get(key);
		if (setting != null) {
			try {
				return Float.valueOf(setting).floatValue();
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	@Override
	public int getInt(String key, int defaultValue) {
		String setting = items.get(key);
		if (setting != null) {
			try {
				return Integer.valueOf(setting).intValue();
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	@Override
	public long getLong(String key, long defaultValue) {
		String setting = items.get(key);
		if (setting != null) {
			try {
				return Long.valueOf(setting).longValue();
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

