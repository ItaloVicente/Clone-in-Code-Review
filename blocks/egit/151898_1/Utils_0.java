	private static final Pattern VERTICAL_SPACES = Pattern.compile("\\v+"); //$NON-NLS-1$

	private Utils() {
	}

	public static String firstLine(String text) {
		Matcher m = VERTICAL_SPACES.matcher(text);
		if (m.find()) {
			return text.substring(0, m.start());
		}
		return text;
	}

	public static boolean isMultiLine(String text) {
		return VERTICAL_SPACES.matcher(text).find();
	}

	public static String toSingleLine(String text) {
		return VERTICAL_SPACES.matcher(text).replaceAll(" "); //$NON-NLS-1$
	}

