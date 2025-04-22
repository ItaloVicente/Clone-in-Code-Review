	protected IResourceVariantTree getSourceTree() {
		if (sourceTree == null)
			sourceTree = new GitSourceResourceVariantTree(cache, gsds);

		return sourceTree;
	}

