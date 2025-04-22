			Repository repository = ((RepositoryNode) element).getRepository();
			if (repository != null) {
				config = new GitFlowConfig(
						DecoratorRepositoryStateCache.INSTANCE
								.getConfig(repository));
			}
