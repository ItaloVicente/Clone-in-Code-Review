	public FileMode getIndexFileMode(final DirCacheIterator indexIter) {
		final FileMode wtMode = getEntryFileMode();
		if (indexIter == null)
			return wtMode;
		if (getOptions().isFileMode())
			return wtMode;
		final FileMode iMode = indexIter.getEntryFileMode();
		if (FileMode.REGULAR_FILE == wtMode
				&& FileMode.EXECUTABLE_FILE == iMode)
			return iMode;
		if (FileMode.EXECUTABLE_FILE == wtMode
				&& FileMode.REGULAR_FILE == iMode)
			return iMode;
		return wtMode;
	}

