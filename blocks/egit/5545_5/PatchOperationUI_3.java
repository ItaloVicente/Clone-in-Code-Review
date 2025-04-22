	private String makeRepoRelative(IResource res) {
		return stripWorkDir(repository.getWorkTree(), res.getLocation()
				.toFile());
	}

	private boolean containsPrefix(Set<String> collection, String prefix) {
		for (String path : collection)
			if (path.startsWith(prefix))
				return true;
		return false;
	}

