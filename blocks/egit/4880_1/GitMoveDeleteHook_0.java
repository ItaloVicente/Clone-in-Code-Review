		try {
			final DirCacheEntry[] sEnt = sCache.getEntriesWithin(sPath);
			if (sEnt.length == 0) {
				sCache.unlock();
				return MoveResult.UNTRACKED;
			}
