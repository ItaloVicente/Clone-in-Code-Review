	private boolean isExpired(Repository db) {
		return db != null && db.useCnt.get() == 0
			&& (System.currentTimeMillis() - db.closedAt.get() > expireAfter);
	}

	private void unregisterAndCloseRepository(final Key location
			Repository db) {
		synchronized (lockFor(location)) {
			if (isExpired(db)) {
				Repository oldDb = unregisterRepository(location);
				if (oldDb != null) {
					oldDb.close();
				}
			}
