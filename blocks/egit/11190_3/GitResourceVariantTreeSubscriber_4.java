	protected IResourceVariantTree getSourceTree() {
		if (sourceTree == null)
			sourceTree = new GitSourceResourceVariantTree(cache, gsds);

		return sourceTree;
	}

	public IFileRevision getSourceFileRevision(IFile resource)
			throws TeamException {
		return syncInfoConverter.getLocalFileRevision(resource);
	}

