	void fireIndexChanged() {
		final IndexChangedEvent event = new IndexChangedEvent(this);
		List<RepositoryListener> all;
		synchronized (listeners) {
			all = new ArrayList<RepositoryListener>(listeners);
		}
		synchronized (allListeners) {
			all.addAll(allListeners);
		}
		for (final RepositoryListener l : all) {
			l.indexChanged(event);
		}
	}
