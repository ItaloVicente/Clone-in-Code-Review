	private Pattern safeCompile(String pattern) {
		try {
			return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		} catch (Exception e) {
			return Pattern.compile("\\a"); //$NON-NLS-1$
		}
	}

