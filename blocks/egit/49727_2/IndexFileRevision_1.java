	private long locateBlobObjectStamp() throws CoreException {
		try {
			DirCache dc = db.readDirCache();
			int firstIndex = dc.findEntry(path);
			if (firstIndex < 0)
				return -1;

			DirCacheEntry firstEntry = dc.getEntry(firstIndex);
			if (stage == FIRST_AVAILABLE || firstEntry.getStage() == stage)
				return firstEntry.getLastModified();

			int nextIndex = dc.nextEntry(firstIndex);
			for (int i = firstIndex; i < nextIndex; i++) {
				DirCacheEntry entry = dc.getEntry(i);
				if (entry.getStage() == stage)
					return entry.getLastModified();
			}
			return -1;
		} catch (IOException e) {
			throw new CoreException(Activator.error(NLS.bind(
					CoreText.IndexFileRevision_errorLookingUpPath, path), e));
		}
	}

