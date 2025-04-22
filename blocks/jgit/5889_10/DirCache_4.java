	public static DirCache lock(final Repository repository
			final IndexChangedListener indexChangedListener)
			throws CorruptObjectException
		DirCache c = lock(repository.getIndexFile()
				indexChangedListener);
		c.repository = repository;
		return c;
	}

