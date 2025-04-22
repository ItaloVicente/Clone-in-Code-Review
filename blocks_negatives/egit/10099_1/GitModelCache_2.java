		for (GitModelTree modelTree : cacheTreeMap.values())
			modelTree.dispose();

		cache.clear();
		cacheTreeMap.clear();
	}

	private GitModelObject extractFromCache(Change change, String path) {
			return handleCacheTree(change, path);

		return fileFactory.createFileModel(this, repo, change,
				location.append(path));
	}

	private GitModelObject handleCacheTree(Change change, String path) {
		String pathKey = path.substring(0, firstSlash);
		GitModelCacheTree cacheTree = cacheTreeMap.get(pathKey);
		if (cacheTree == null) {
			IPath newPath = location.append(pathKey);
			cacheTree = new GitModelCacheTree(this, repo, newPath, fileFactory);
			cacheTreeMap.put(pathKey, cacheTree);
