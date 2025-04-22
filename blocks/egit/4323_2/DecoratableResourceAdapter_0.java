
	private boolean containsPrefixPath(Set<String> collection, String path) {
		for (String entry : collection)
			if (path.startsWith(entry))
				return true;
		return false;
	}

