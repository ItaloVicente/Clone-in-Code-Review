		Collection<? extends Repository> repositories = null;
		if (node instanceof RepositoryGroupNode) {
			repositories = ((RepositoryGroupNode) node).getRepositories();
		} else {
			Repository repository = node.getRepository();
			if (repository != null) {
				repositories = Collections.singleton(repository);
			}
		}
		if (repositories == null || repositories.isEmpty()) {
