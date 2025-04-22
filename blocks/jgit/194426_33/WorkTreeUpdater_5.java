	public void addExistingToIndex(DirCacheEntry dce) {
		DirCacheEntry oldEntry = builder.getDirCache().getEntry(dce.getPathString());
		builder.add(dce);
		if (oldEntry == null || !oldEntry.getObjectId().equals(dce.getObjectId()))
	}

	private ObjectId insertResult(StreamLoader resultStreamLoader
			Attribute lfsAttribute) throws IOException {
		try (LfsInputStream is = org.eclipse.jgit.util.LfsFactory.getInstance()
				.applyCleanFilter(repo
						resultStreamLoader.size
