	private DirCacheEntry oursDce(DirCacheBuildIterator index) {
		DirCacheEntry ourDce = null;
		if (index == null || index.getDirCacheEntry() == null) {
			if (nonTree(tw.getRawMode(T_OURS))) {
				ourDce = new DirCacheEntry(tw.getRawPath(T_OURS));
				ourDce.setObjectId(tw.getObjectId(T_OURS));
				ourDce.setFileMode(tw.getFileMode(T_OURS));
			}
		} else {
			ourDce = index.getDirCacheEntry();
		}
		return ourDce;
	}

