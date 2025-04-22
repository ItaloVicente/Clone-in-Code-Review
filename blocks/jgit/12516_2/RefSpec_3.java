		if (isWildcard()) {
			int wildcardIndex = s.indexOf('*');
			String prefix = s.substring(0
			String suffix = s.substring(wildcardIndex + 1);
			return refName.startsWith(prefix) && refName.endsWith(suffix)
					&& refName.length() > prefix.length() + suffix.length();
		} else {
			return refName.equals(s);
		}
	}

	private static String expandWildcard(String refName
			String toPattern) {
		int fromWildcard = fromPattern.indexOf('*');
		int fromTrailing = fromPattern.length() - (fromWildcard + 1);
		int toWildcard = toPattern.indexOf('*');
		String wildcardPart = refName.substring(fromWildcard
				- fromTrailing);
		return toPattern.substring(0
				+ toPattern.substring(toWildcard + 1);
	}

	private static boolean isValid(final String s) {
		final int includeTrailingEmpty = -1;
		final String[] parts = s.split("/"
		boolean wildcard = false;
		for (int i = 0; i < parts.length; i++) {
			String part = parts[i];
			if (part.length() == 0)
				return false;
				if (wildcard)
					return false;
				wildcard = true;
				return false;
			}
		}
		return true;
