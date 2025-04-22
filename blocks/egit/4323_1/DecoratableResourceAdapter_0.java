
	private boolean containsPrefixPath(Set<String> collection, String path) {
		if (path.length() == 1 && !collection.isEmpty())
			return true;

		for (String entry : collection)
			if (path.startsWith(entry))
				return true;
		return false;
	}

