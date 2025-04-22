	public FileMode getEntryFileMode(FileMode indexMode
		FileMode wtMode = getEntryFileMode();
		if (indexMode == FileMode.EXECUTABLE_FILE
				&& wtMode == FileMode.REGULAR_FILE && !fs.supportsExecute())
			return indexMode;
		return wtMode;
	}

