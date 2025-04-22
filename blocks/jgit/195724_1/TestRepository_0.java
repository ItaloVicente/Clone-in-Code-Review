	public DirCacheEntry link(String path
		DirCacheEntry e = new DirCacheEntry(path);
		e.setFileMode(FileMode.SYMLINK);
		e.setObjectId(blob);
		return e;
	}

