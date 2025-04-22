				repositoryName = DecoratorRepositoryStateCache.INSTANCE
						.getRepositoryNameAndState(repository);
				branch = DecoratorRepositoryStateCache.INSTANCE
						.getCurrentBranchLabel(repository);
				branchStatus = DecoratorRepositoryStateCache.INSTANCE
						.getBranchStatus(repository);
				RevCommit headCommit = DecoratorRepositoryStateCache.INSTANCE
