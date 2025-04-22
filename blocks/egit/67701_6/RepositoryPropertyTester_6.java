		Repository repository = null;
		if (receiver instanceof String) {
			String gitDir = (String) receiver;
			repository = org.eclipse.egit.core.Activator.getDefault()
					.getRepositoryCache().getRepository(new File(gitDir));
		} else if (receiver instanceof Repository) {
			repository = (Repository) receiver;
		}
		if (repository == null || repository.isBare()) {
