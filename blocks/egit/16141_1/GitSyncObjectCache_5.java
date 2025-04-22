	private static boolean containsPathOrParent(Set<String> filterPaths,
			String pathToTest) {
		if (filterPaths.contains(pathToTest))
			return true;
		if (filterPaths.contains("")) //$NON-NLS-1$
			return true;
		String path = pathToTest;
		int lastSlash = path.lastIndexOf('/');
		while (lastSlash != -1) {
			path = path.substring(0, lastSlash);
			if (filterPaths.contains(path))
				return true;
			lastSlash = path.lastIndexOf('/');
		}
		return false;
	}
