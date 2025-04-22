		if (mergeStrategyRegistryListener == null) {
			return Collections.emptyList();
		}
		return mergeStrategyRegistryListener.getStrategies();
	}

	private void registerMergeStrategyRegistryListener() {
		mergeStrategyRegistryListener = new MergeStrategyRegistryListener(
				Platform.getExtensionRegistry());
		Platform.getExtensionRegistry().addListener(
				mergeStrategyRegistryListener,
	}

	/**
	 * @return cache for Repository objects
	 */
	public RepositoryCache getRepositoryCache() {
		return repositoryCache;
	}

	/**
	 *  @return cache for index diffs
	 */
	public IndexDiffCache getIndexDiffCache() {
		return indexDiffCache;
	}

	/**
	 * @return the {@link RepositoryUtil} instance
	 */
	public RepositoryUtil getRepositoryUtil() {
		return repositoryUtil;
