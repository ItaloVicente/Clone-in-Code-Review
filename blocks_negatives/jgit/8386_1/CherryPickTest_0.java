
	private DirCacheEntry makeEntry(final String path, final FileMode mode)
			throws Exception {
		return makeEntry(path, mode, path);
	}

	private DirCacheEntry makeEntry(final String path, final FileMode mode,
			final String content) throws Exception {
		final DirCacheEntry ent = new DirCacheEntry(path);
		ent.setFileMode(mode);
		ent.setObjectId(new ObjectInserter.Formatter().idFor(OBJ_BLOB,
				Constants.encode(content)));
		return ent;
	}
