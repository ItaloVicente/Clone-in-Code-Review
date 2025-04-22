		if (isWildcard()) {
			int wildcardIndex = s.indexOf('*');
			String prefix = s.substring(0
			String suffix = s.substring(wildcardIndex + 1);
			return name.length() > prefix.length() + suffix.length()
					&& name.startsWith(prefix) && name.endsWith(suffix);
		}
		return name.equals(s);
	}

	private static String expandWildcard(String name
			String patternB) {
		int a = patternA.indexOf('*');
		int trailingA = patternA.length() - (a + 1);
		int b = patternB.indexOf('*');
		String match = name.substring(a
		return patternB.substring(0
	}

	private static String checkValid(String spec) {
		if (spec != null && !isValid(spec))
			throw new IllegalArgumentException(MessageFormat.format(
					JGitText.get().invalidRefSpec
		return spec;
	}

	private static boolean isValid(final String s) {
			return false;
			return false;
		int i = s.indexOf('*');
		if (i != -1) {
			if (s.indexOf('*'
				return false;
			if (i > 0 && s.charAt(i - 1) != '/')
				return false;
			if (i < s.length() - 1 && s.charAt(i + 1) != '/')
				return false;
		}
		return true;
