	public void onIndexChanged(IndexChangedEvent e) {
		postLabelEvent();
	}

	public void onRefsChanged(RefsChangedEvent e) {
		postLabelEvent();
	}

	/**
	 * Callback for RepositoryChangeListener events, as well as
	 * RepositoryListener events via repositoryChanged()
	 *
	 * @see org.eclipse.egit.core.project.RepositoryChangeListener#repositoryChanged(org.eclipse.egit.core.project.RepositoryMapping)
	 */
	public void repositoryChanged(RepositoryMapping mapping) {
