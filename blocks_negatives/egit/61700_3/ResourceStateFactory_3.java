	@Nullable
	private String makeRepositoryRelative(Repository repository,
			IResource res) {
		IPath location = res.getLocation();
		if (location == null) {
			return null;
		}
		if (repository.isBare()) {
			return null;
		}
		File workTree = repository.getWorkTree();
		return stripWorkDir(workTree, location.toFile());
	}

