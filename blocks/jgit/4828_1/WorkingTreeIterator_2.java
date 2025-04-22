	public boolean useIndexMode(FileMode indexFileMode
		return indexFileMode == FileMode.EXECUTABLE_FILE
				&& getEntryFileMode() == FileMode.REGULAR_FILE
				&& !fs.supportsExecute();
	}

