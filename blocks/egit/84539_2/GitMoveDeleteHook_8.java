	private MoveResult checkUnmergedPaths(final RepositoryMapping srcm,
			final String sPath) throws IOException {
		final DirCache sCache = srcm.getRepository().lockDirCache();
		try {
			final DirCacheEntry[] sEnt = sCache.getEntriesWithin(sPath);
			if (sEnt.length == 0) {
				sCache.unlock();
				return MoveResult.UNTRACKED;
			}
			for (final DirCacheEntry se : sEnt) {
				if (!se.isMerged()) {
					return MoveResult.UNMERGED;
				}
			}
			return MoveResult.SUCCESS;
		} finally {
			if (sCache != null) {
				sCache.unlock();
			}
		}
	}

