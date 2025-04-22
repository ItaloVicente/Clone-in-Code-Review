	public static String getMessage(String key, Object... args) {
		try {
			return MessageFormat.format(RESOURCE_BUNDLE.getString(key), args);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
