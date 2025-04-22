	/**
	 * Notify registered {@link RepositoryChangeListener}s of a change.
	 *
	 * @see GitProjectData#addRepositoryChangeListener(RepositoryChangeListener)
	 */
	public void fireRepositoryChanged() {
		GitProjectData.fireRepositoryChanged(this);
	}

