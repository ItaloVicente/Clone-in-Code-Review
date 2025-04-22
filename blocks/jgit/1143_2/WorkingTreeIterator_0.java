
		if (fileLastModified != cacheLastModified) {
			if (forceContentCheck) {
				return contentCheck(entry);
			} else {
				return true;
			}
