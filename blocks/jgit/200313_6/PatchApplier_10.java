		for (int i = 0; i < dirCache.getEntryCount(); i++) {
			DirCacheEntry dce = dirCache.getEntry(i);
			if (!modifiedPaths.contains(dce.getPathString())
					|| dce.getStage() != DirCacheEntry.STAGE_0)
				dirCacheBuilder.add(dce);
		}
