
	protected DirCacheEntry createEntry(final String path
		return createEntry(path
	}

	protected DirCacheEntry createEntry(final String path
			final String content) {
		return createEntry(path
	}

	protected DirCacheEntry createEntry(final String path
			final int stage
		final DirCacheEntry entry = new DirCacheEntry(path
		entry.setFileMode(mode);
		entry.setObjectId(new ObjectInserter.Formatter().idFor(
				Constants.OBJ_BLOB
		return entry;
	}
