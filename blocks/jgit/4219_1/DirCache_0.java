	public static DirCache lock(final File indexLocation
			IndexChangedListener indexChangedListener)
			throws CorruptObjectException
			IOException {
		DirCache c = lock(indexLocation
		c.registerIndexChangedListener(indexChangedListener);
		return c;
	}

