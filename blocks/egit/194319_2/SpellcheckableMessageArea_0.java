	public void addCleanupChangeListener(Runnable listener) {
		cleanupChangeListeners.addIfAbsent(listener);
	}

	public void removeCleanupChangeListener(Runnable listener) {
		cleanupChangeListeners.remove(listener);
	}

