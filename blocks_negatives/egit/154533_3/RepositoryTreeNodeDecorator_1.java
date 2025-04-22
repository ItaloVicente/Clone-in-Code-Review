	@Override
	public void indexDiffChanged(Repository repository,
			IndexDiffData indexDiffData) {
		DecoratorRepositoryStateCache.INSTANCE.clear(repository);
		super.indexDiffChanged(repository, indexDiffData);
	}

