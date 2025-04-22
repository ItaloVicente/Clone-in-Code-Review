		long cacheLastModified = entry.getLastModified();
		long fileLastModified = getEntryLastModified();
		long lastModifiedMillis = fileLastModified % 1000;
		long cacheMillis = cacheLastModified % 1000;
		if (getOptions().getCheckStat() == CheckStat.MINIMAL) {
			fileLastModified = fileLastModified - lastModifiedMillis;
			cacheLastModified = cacheLastModified - cacheMillis;
		} else if (cacheMillis == 0)
			fileLastModified = fileLastModified - lastModifiedMillis;
		else if (lastModifiedMillis == 0)
			cacheLastModified = cacheLastModified - cacheMillis;

		if (fileLastModified != cacheLastModified)
