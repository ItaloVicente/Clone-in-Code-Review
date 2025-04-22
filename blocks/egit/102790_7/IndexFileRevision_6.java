
		DirCacheEntry firstEntry = cache.getEntry(firstIndex);
		if (stage == FIRST_AVAILABLE || firstEntry.getStage() == stage)
			return firstEntry.getObjectId();

		int nextIndex = cache.nextEntry(firstIndex);
		for (int i = firstIndex; i < nextIndex; i++) {
			DirCacheEntry entry = cache.getEntry(i);
			if (entry.getStage() == stage)
				return entry.getObjectId();
