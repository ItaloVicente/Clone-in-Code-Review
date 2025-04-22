	public boolean isModified(DirCacheEntry entry
			boolean checkFilemode
		if (entry.isAssumeValid())
			return false;

		if (entry.isUpdateNeeded())
			return true;

		if (getEntryLength() != entry.getLength())
			return true;

		int modeDiff = getEntryRawMode() ^ entry.getRawMode();
		if (!checkFilemode)
			modeDiff &= ~FileMode.EXECUTABLE_FILE.getBits();
		if (modeDiff != 0)
			return true;

		long cacheLastModified = entry.getLastModified();
		long fileLastModified = getEntryLastModified();
		if (cacheLastModified % 1000 == 0)
			fileLastModified = fileLastModified - fileLastModified % 1000;
		if (forceContentCheck) {
			if (fileLastModified == cacheLastModified)
			else
				return !getEntryObjectId().equals(entry.getObjectId());
		} else {
			return fileLastModified != cacheLastModified;
		}
	}

