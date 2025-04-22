	protected DirCacheEntry createGitLink(String path
		final DirCacheEntry entry = new DirCacheEntry(path
				DirCacheEntry.STAGE_0);
		entry.setFileMode(FileMode.GITLINK);
		entry.setObjectId(objectId);
		return entry;
	}

