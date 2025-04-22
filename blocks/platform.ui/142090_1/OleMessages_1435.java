	public static String getString(String key) {
		try {
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}
