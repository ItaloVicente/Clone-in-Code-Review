	public static String getString(String key, String def) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return def;
		}
	}
