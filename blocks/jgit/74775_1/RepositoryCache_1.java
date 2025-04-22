	private void clearAllExpired() {
		for (Reference<Repository> ref : cacheMap.values()) {
			Repository db = ref.get();
			if (db != null && db.useCnt.get() == 0
					&& (System.currentTimeMillis()
							- db.closedAt.get() > 20000)) {
				RepositoryCache.close(db);
			}
		}
	}

