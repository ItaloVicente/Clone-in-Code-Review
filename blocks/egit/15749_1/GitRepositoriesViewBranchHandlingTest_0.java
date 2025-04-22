
		RepositoryUtil repositoryUtil = Activator.getDefault()
				.getRepositoryUtil();
		repositoryUtil.addConfiguredRepository(repositoryFile);
		repositoryUtil.addConfiguredRepository(remoteRepositoryFile);
		repositoryUtil.addConfiguredRepository(clonedRepositoryFile);
