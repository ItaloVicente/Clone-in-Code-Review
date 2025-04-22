		long lastModifiedMillis = fileLastModified % 1000;
		long cacheMillis = cacheLastModified % 1000;
		if (getOptions().getCheckStat() == CheckStat.MINIMAL) {
			fileLastModified = fileLastModified - lastModifiedMillis;
			cacheLastModified = cacheLastModified - cacheMillis;
		} else if (cacheMillis == 0)
			fileLastModified = fileLastModified - lastModifiedMillis;
