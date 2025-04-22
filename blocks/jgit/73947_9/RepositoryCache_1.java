	private void unregisterAndCloseRepository(final Key location
			Repository db) {
		synchronized (lockFor(location)) {
			if (db.useCnt.get() == 0) {
				Repository oldDb = unregisterRepository(location);
				if (oldDb != null) {
					oldDb.close();
				}
			}
