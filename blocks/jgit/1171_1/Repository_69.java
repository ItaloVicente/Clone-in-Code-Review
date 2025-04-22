	public DirCache readDirCache() throws NoWorkTreeException
			CorruptObjectException
		return DirCache.read(getIndexFile());
	}

	public DirCache lockDirCache() throws NoWorkTreeException
			CorruptObjectException
		return DirCache.lock(getIndexFile());
	}

