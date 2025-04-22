		DirCacheIterator cacheIterator = tw.getTree(dirCacheIteratorNth,
				DirCacheIterator.class);
		if (cacheIterator == null)
			return null;

		DirCacheEntry cacheEntry = cacheIterator.getDirCacheEntry();
		if (cacheEntry == null)
			return null;

