	private static int indexOfUnescaped(final String searchString
			final char ch
		for (int i = fromIndex; i < searchString.length(); i++) {
			char current = searchString.charAt(i);
			if (current == ch)
				return i;
			if (current == '\\')
		}
		return -1;
	}

