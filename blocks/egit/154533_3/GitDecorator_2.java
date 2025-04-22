		DecoratorRepositoryStateCache.INSTANCE.clear(repository);
		postLabelEvent();
	}

	@Override
	public void onConfigChanged(ConfigChangedEvent event) {
		DecoratorRepositoryStateCache.INSTANCE
				.resetBranchState(event.getRepository());
