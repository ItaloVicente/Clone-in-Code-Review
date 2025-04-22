	public boolean isModified(DirCacheEntry entry
			boolean checkFilemode
		if (entry.isAssumeValid())
			return false;

		if (getEntryLength() != entry.getLength())
			return true;

		if (checkFilemode) {
			if (FileMode.EXECUTABLE_FILE.equals(entry.getRawMode())
					&& !fs.supportsExecute()) {
				if (!FileMode.REGULAR_FILE.equals(getEntryRawMode()))
					return true;
			} else {
				if (entry.getRawMode() != getEntryRawMode())
					return true;
			}
		}

		long cacheLastModified = entry.getLastModified();
		long fileLastModified = getEntryLastModified();
		if (cacheLastModified % 1000 == 0)
			fileLastModified = fileLastModified - fileLastModified % 1000;
		if ((fileLastModified != cacheLastModified) && !forceContentCheck)
			return true;

		return (!getEntryObjectId().equals(entry.getObjectId()));
	}

