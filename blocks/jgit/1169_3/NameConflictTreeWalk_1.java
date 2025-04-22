
		if (ch == dfConflict)
			dfConflict = null;
	}

	public boolean isDirectoryFileConflict() {
		return dfConflict != null;
