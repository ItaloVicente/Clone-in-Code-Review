		if (location.toFile().isDirectory()) {
			for (DirCacheEntry ent : cache.getEntriesWithin(path)) {
				ent.setAssumeValid(assumeUnchanged);
			}
		} else {
			DirCacheEntry ent = cache.getEntry(path);
			if (ent != null) {
				ent.setAssumeValid(assumeUnchanged);
			}
