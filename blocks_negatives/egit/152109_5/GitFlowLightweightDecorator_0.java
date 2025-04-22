			handleException(repository, e);
		}
	}

	private static @Nullable GitFlowRepository getRepository(Object element) {
		GitFlowRepository repository = null;
		if (element instanceof GitFlowRepository) {
			repository = (GitFlowRepository) element;
		}

		if (element instanceof RepositoryNode) {
			RepositoryNode node = (RepositoryNode) element;
			Repository repo = node.getObject();
			if (repo != null) {
				repository = new GitFlowRepository(repo);
			}
