	private static Set<String> mergeIgnored(Set<String> oldIgnoredPaths,
			Collection<String> changedPaths, Set<String> newIgnoredPaths) {
		Set<String> merged = new HashSet<String>();
		for (String oldIgnoredPath : oldIgnoredPaths) {
			boolean changed = isAnyPrefixOf(oldIgnoredPath, changedPaths);
			if (!changed)
				merged.add(oldIgnoredPath);
		}
		merged.addAll(newIgnoredPaths);
		return merged;
	}

	private static boolean isAnyPrefixOf(String pathToCheck, Collection<String> possiblePrefixes) {
		for (String possiblePrefix : possiblePrefixes)
			if (pathToCheck.startsWith(possiblePrefix) || possiblePrefix.equals(pathToCheck + '/'))
				return true;
		return false;
	}

