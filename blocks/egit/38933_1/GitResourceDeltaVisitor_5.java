		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		IndexDiffCacheEntry entry = null;

		if (cache != null)
			entry = cache.getIndexDiffCacheEntry(mapping.getRepository());

