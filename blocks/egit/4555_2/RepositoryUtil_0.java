	public String getRepositoryName(final Repository repository) {
		File gitDir = repository.getDirectory();
		if (gitDir == null)
			return ""; //$NON-NLS-1$

		if (!repository.isBare()) {
			gitDir = gitDir.getParentFile();
			if (gitDir == null)
				return ""; //$NON-NLS-1$
		}

