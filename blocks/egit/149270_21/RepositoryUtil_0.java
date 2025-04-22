	public String getRepositorySpecificPreferenceKey(String repositoryId,
			String preferenceKey) {
		return preferenceKey + "_" + repositoryId; //$NON-NLS-1$
	}

	public String getRepositorySpecificPreferenceKey(
			@NonNull Repository repository, String preferenceKey) {
		String pathString = getRelativizedRepositoryPath(repository);

		if (pathString == null) {
			return getRepositorySpecificPreferenceKey(repository.toString(),
					preferenceKey);
		}

		return getRepositorySpecificPreferenceKey(pathString, preferenceKey);
	}

