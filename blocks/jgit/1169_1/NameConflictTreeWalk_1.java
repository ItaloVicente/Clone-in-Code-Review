
	public boolean isDirectoryFileConflict() {
		if (dfConflictPath != null) {
			if ((currentHead.getEntryPathString()+"/").startsWith(dfConflictPath))
				return true;
			dfConflictPath = null;
		}
		return false;
	}
