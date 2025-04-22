		return oldRef != null ? oldRef.get() : null;
	}

	private void unregisterAndCloseRepository(final Key location) {
		Repository oldDb = unregisterRepository(location);
		if (oldDb != null) {
