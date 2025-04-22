	private @Nullable Repository getRepository() {
		if (Activator.getDefault() == null) {
			return null;
		}
		Repository repository = Activator.getDefault().getRepositoryCache()
				.getRepository(repositoryGitDir);
		if (repository == null) {
			return null;
		}
		File directory = repository.getDirectory();
		if (directory == null || !directory.exists()) {
			return null;
		}
		return repository;
	}

