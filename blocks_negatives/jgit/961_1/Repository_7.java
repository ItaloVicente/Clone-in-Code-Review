	/**
	 * Register a {@link RepositoryListener} which will be notified
	 * when ref changes are detected.
	 *
	 * @param l
	 */
	public void addRepositoryChangedListener(final RepositoryListener l) {
		listeners.add(l);
	}

	/**
	 * Remove a registered {@link RepositoryListener}
	 * @param l
	 */
	public void removeRepositoryChangedListener(final RepositoryListener l) {
		listeners.remove(l);
	}

	/**
	 * Register a global {@link RepositoryListener} which will be notified
	 * when a ref changes in any repository are detected.
	 *
	 * @param l
	 */
	public static void addAnyRepositoryChangedListener(final RepositoryListener l) {
		allListeners.add(l);
	}

	/**
	 * Remove a globally registered {@link RepositoryListener}
	 * @param l
	 */
	public static void removeAnyRepositoryChangedListener(final RepositoryListener l) {
		allListeners.remove(l);
	}

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
		}
	}

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

