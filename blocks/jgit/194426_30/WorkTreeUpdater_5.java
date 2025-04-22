	public void addExistingToIndex(DirCacheEntry dce) {
		DirCacheEntry oldEntry = builder.getDirCache().getEntry(dce.getPathString());
		builder.add(dce);
		if (oldEntry == null || !oldEntry.getObjectId().equals(dce.getObjectId()))
			markAsModified(dce.getPathString());
	}
