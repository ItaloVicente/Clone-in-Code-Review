	private static String getRepoSpecificKey(String repositoryId,
			String key) {
	}

	private static String getRepoSpecificKey(@NonNull Repository repo,
			String key) {
		String pathString = Activator.getDefault().getRepositoryUtil()
				.getRelativizedRepositoryPath(repo);

		if (pathString == null) {
			return getRepoSpecificKey(repo.toString(), key);
		}

		return getRepoSpecificKey(pathString, key);
	}

