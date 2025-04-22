		final String path = rm.getRepoRelativePath(resource);
		if (resource instanceof IContainer) {
			for (final DirCacheEntry ent : cache.getEntriesWithin(path)) {
				ent.setAssumeValid(assumeUnchanged);
			}
		} else {
			final DirCacheEntry ent = cache.getEntry(path);
			if (ent != null) {
				ent.setAssumeValid(assumeUnchanged);
			}
