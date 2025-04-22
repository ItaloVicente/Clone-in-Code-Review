	private DirCacheEntry keep(DirCacheEntry e) {
		DirCacheEntry newEntry = new DirCacheEntry(e.getPathString()
				e.getStage());
		newEntry.setFileMode(e.getFileMode());
		newEntry.setObjectId(e.getObjectId());
		newEntry.setLastModified(e.getLastModified());
		newEntry.setLength(e.getLength());
		builder.add(newEntry);
		return newEntry;
	}

