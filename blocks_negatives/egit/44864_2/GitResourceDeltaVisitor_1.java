		IndexDiffCache cache = Activator.getDefault().getIndexDiffCache();
		IndexDiffCacheEntry entry = null;

		if (cache != null)
			entry = cache.getIndexDiffCacheEntry(mapping.getRepository());

		if (resource instanceof IFolder
				&& delta.getKind() == IResourceDelta.ADDED) {
