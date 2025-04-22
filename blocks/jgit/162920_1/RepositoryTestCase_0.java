	protected DirCacheEntry createGitLink(String path
		final DirCacheEntry entry = new DirCacheEntry(path
				DirCacheEntry.STAGE_0);
		entry.setFileMode(FileMode.GITLINK);
		try (ObjectInserter.Formatter formatter = new ObjectInserter.Formatter()) {
			entry.setObjectId(objectId);
		}
		return entry;
	}

