	public UnmodifiableDirCache getDirCache() throws NoWorkTreeException
			CorruptObjectException
		if (readOnlyDirCache == null || readOnlyDirCache.isOutdated())
			readOnlyDirCache = new UnmodifiableDirCache(getIndexFile()
		return readOnlyDirCache;
	}

