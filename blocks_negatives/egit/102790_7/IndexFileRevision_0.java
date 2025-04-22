	private ObjectId locateBlobObjectId() throws CoreException {
		try {
			DirCache dc = db.readDirCache();
			int firstIndex = dc.findEntry(path);
			if (firstIndex < 0)
				return null;

			DirCacheEntry firstEntry = dc.getEntry(firstIndex);
			if (stage == FIRST_AVAILABLE || firstEntry.getStage() == stage)
				return firstEntry.getObjectId();

			int nextIndex = dc.nextEntry(firstIndex);
			for (int i = firstIndex; i < nextIndex; i++) {
				DirCacheEntry entry = dc.getEntry(i);
				if (entry.getStage() == stage)
					return entry.getObjectId();
