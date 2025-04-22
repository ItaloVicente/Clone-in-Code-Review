		final DirCache dc = DirCache.newInCore();
		final DirCacheBuilder b = dc.builder();
		for (final DirCacheEntry e : entries)
			b.add(e);
		b.finish();
		return rw.lookupTree(dc.writeTree(ow));
