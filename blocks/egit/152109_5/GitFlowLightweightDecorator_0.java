		GitFlowConfig config = null;
		if (element instanceof GitFlowRepository) {
			config = ((GitFlowRepository) element).getConfig();
		} else if (element instanceof RepositoryNode) {
			config = new GitFlowConfig(DecoratorRepositoryStateCache.INSTANCE
					.getConfig(((RepositoryNode) element).getRepository()));
