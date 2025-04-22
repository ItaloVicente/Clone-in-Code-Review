	void addChild(Change change, String nestedPath) {
		String pathKey;
		if (firstSlash > -1)
			pathKey = nestedPath.substring(0, firstSlash);
		else
			pathKey = nestedPath;

		IPath fullPath = getLocation().append(pathKey);
			GitModelCacheTree cacheEntry = (GitModelCacheTree) cacheTreeMap
					.get(pathKey);
			if (cacheEntry == null) {
				cacheEntry = new GitModelCacheTree(this, repo, fullPath, factory);
				cacheTreeMap.put(pathKey, cacheEntry);
			}
			cacheEntry.addChild(change, nestedPath.substring(firstSlash + 1));
		} else
			cacheTreeMap.put(pathKey,
					factory.createFileModel(this, repo, change, fullPath));
	}

