	void fireRefsChanged() {
		final RefsChangedEvent event = new RefsChangedEvent(this);
		List<RepositoryListener> all;
		synchronized (listeners) {
			all = new ArrayList<RepositoryListener>(listeners);
		}
		synchronized (allListeners) {
			all.addAll(allListeners);
		}
		for (final RepositoryListener l : all) {
			l.refsChanged(event);
