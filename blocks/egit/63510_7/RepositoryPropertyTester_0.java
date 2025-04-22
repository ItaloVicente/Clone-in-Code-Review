		RepositorySourceProvider sp = (RepositorySourceProvider) sps
				.getSourceProvider(
						RepositorySourceProvider.REPOSITORY_PROPERTY);
		if (sp == null) {
			return false;
		}
		Repository repository = sp.waitFor();
		if (repository == null || repository.isBare()) {
