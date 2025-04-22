		this.inCore = inCore;

		if (inCore) {
			dircache = DirCache.newInCore();
		}
	}

	protected ResolveMerger(Repository local) {
		this(local
