	public static boolean isAnyPrefixOf(String pathToCheck,
			Collection<String> possiblePrefixes) {
		for (String possiblePrefix : possiblePrefixes) {
			if (pathToCheck.startsWith(possiblePrefix)) {
				return true;
			}
			if (possiblePrefix.length() == pathToCheck.length() + 1
					&& possiblePrefix.charAt(possiblePrefix.length() - 1) == '/'
					&& possiblePrefix.startsWith(pathToCheck)) {
