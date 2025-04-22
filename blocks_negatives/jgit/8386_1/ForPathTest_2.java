	private DirCacheEntry makeEntry(final String path, final FileMode mode)
			throws Exception {
		final DirCacheEntry ent = new DirCacheEntry(path);
		ent.setFileMode(mode);
		ent.setObjectId(new ObjectInserter.Formatter().idFor(
				Constants.OBJ_BLOB, Constants.encode(path)));
		return ent;
	}

