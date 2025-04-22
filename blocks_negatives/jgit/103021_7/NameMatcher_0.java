	public boolean matches(String path, boolean assumeDirectory) {
		int end = 0;
		int firstChar = 0;
		do {
			firstChar = getFirstNotSlash(path, end);
			end = getFirstSlash(path, firstChar);
			boolean match = matches(path, firstChar, end, assumeDirectory);
			if (match)
