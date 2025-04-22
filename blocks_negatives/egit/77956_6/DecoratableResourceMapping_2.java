
	@Nullable
	private String makeRepoRelative(Repository repository, IResource res) {
		if (repository.isBare()) {
			return null;
		}
		IPath location = res.getLocation();
		if (location == null) {
			return null;
		}
		return stripWorkDir(repository.getWorkTree(), location.toFile());
	}

	private boolean containsPrefix(Set<String> collection, String prefix) {
		if (prefix.length() == 1 && !collection.isEmpty())
			return true;

		for (String path : collection)
			if (path.startsWith(prefix))
				return true;
		return false;
	}
