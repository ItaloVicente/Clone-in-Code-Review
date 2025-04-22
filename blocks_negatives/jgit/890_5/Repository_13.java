	void fireRefsChanged() {
		final RefsChangedEvent event = new RefsChangedEvent(this);
		List<RepositoryListener> all;
		synchronized (listeners) {
			all = new ArrayList<RepositoryListener>(listeners);
		}
