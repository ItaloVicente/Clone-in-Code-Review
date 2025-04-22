	 * @since 4.8
	 */
	protected ResolveMerger(ObjectInserter inserter, Config config) {
		super(inserter);
		mergeAlgorithm = getMergeAlgorithm(config);
		commitNames = defaultCommitNames();
		inCore = true;
		implicitDirCache = false;
		dircache = DirCache.newInCore();
	}

	/**
	 * Retrieves the content merge strategy for content conflicts.
	 *
	 * @return the {@link ContentMergeStrategy} in effect
	 * @since 5.12
	 */
	@NonNull
	public ContentMergeStrategy getContentMergeStrategy() {
		return contentStrategy;
	}

	/**
	 * Sets the content merge strategy for content conflicts.
	 *
