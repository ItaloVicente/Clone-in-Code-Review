		File gitDir = repository.getDirectory();
		if (gitDir != null)
			repositoryName = repository.getDirectory().getParentFile()
					.getName();
		else
			repositoryName = ""; //$NON-NLS-1$
