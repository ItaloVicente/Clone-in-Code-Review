	public DirCacheEntry(DirCacheEntry src) {
		path = src.path;
		info = new byte[INFO_LEN];
		infoOffset = 0;
		System.arraycopy(src.info
	}

