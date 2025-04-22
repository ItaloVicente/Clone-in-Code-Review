	protected ResolveMerger(ObjectInserter inserter
		super(inserter);
		mergeAlgorithm = getMergeAlgorithm(config);
		commitNames = defaultCommitNames();
		inCore = true;
		implicitDirCache = false;
		dircache = DirCache.newInCore();
	}

