	public static DirCache read(final Repository repository)
			throws CorruptObjectException
		final DirCache c = read(repository.getIndexFile()
		c.repository = repository;
		return c;
	}

