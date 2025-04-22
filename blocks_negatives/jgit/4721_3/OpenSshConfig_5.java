	private static String dequote(final String value) {
		if (value.startsWith("\"") && value.endsWith("\""))
			return value.substring(1, value.length() - 1);
		return value;
	}

	private static String nows(final String value) {
		final StringBuilder b = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isSpaceChar(value.charAt(i)))
				b.append(value.charAt(i));
		}
		return b.toString();
	}

	private static Boolean yesno(final String value) {
		if (StringUtils.equalsIgnoreCase("yes", value))
			return Boolean.TRUE;
		return Boolean.FALSE;
	}

	private File toFile(final String path) {
		if (path.startsWith("~/"))
			return new File(home, path.substring(2));
		File ret = new File(path);
		if (ret.isAbsolute())
			return ret;
		return new File(home, path);
	}

	static String userName() {
