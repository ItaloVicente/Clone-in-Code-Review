	@Nullable
	static IndexDiffData getIndexDiffDataOrNull(IResource resource) {
		if (resource.getType() == IResource.ROOT) {
			return null;
		}

		if (!resource.exists() && !resource.isPhantom()) {
			return null;
		}

		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null) {
			return null;
		}

		Repository repo = mapping.getRepository();
		if(repo == null){
			return null;
		}

		if (repo.isBare()) {
			return new IndexDiffData();
		}
		
		if (mapping.getRepoRelativePath(resource) == null) {
			return null;
		}

		IndexDiffCacheEntry diffCacheEntry = org.eclipse.egit.core.Activator
				.getDefault().getIndexDiffCache()
				.getIndexDiffCacheEntry(repo);
		if (diffCacheEntry == null) {
			return null;
		}
		return diffCacheEntry.getIndexDiff();
	}

