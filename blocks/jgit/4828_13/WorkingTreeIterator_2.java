	public FileMode getIndexFileMode(final DirCacheIterator indexIter) {
		final FileMode wtMode = getEntryFileMode();
		if (indexIter != null && !getOptions().isFileMode()
				&& FileMode.REGULAR_FILE == wtMode
				&& FileMode.EXECUTABLE_FILE == indexIter.getEntryFileMode())
			return FileMode.EXECUTABLE_FILE;
		return wtMode;
	}

