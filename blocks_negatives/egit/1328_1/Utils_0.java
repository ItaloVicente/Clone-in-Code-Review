	/**
	 * @param repository
	 * @return display name of the repository
	 */
	public static String getRepositoryName(Repository repository) {
		String repositoryName;
		File gitDir = repository.getDirectory();
		if (gitDir != null)
			repositoryName = repository.getDirectory().getParentFile()
					.getName();
		else
		return repositoryName;
	}

