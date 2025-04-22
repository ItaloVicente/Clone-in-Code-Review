		return readDirCache(false);
	}

	public DirCache readDirCache(boolean readOnly) throws NoWorkTreeException
			CorruptObjectException
		return DirCache.read(getIndexFile()
