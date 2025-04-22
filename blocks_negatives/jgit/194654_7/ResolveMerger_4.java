	 */
	protected ResolveMerger(Repository local, boolean inCore) {
		super(local);
		Config config = local.getConfig();
		mergeAlgorithm = getMergeAlgorithm(config);
		inCoreLimit = getInCoreLimit(config);
		commitNames = defaultCommitNames();
		this.inCore = inCore;

		if (inCore) {
			implicitDirCache = false;
			dircache = DirCache.newInCore();
		} else {
			implicitDirCache = true;
			workingTreeOptions = local.getConfig().get(WorkingTreeOptions.KEY);
		}
	}

	/**
	 * Constructor for ResolveMerger.
	 *
