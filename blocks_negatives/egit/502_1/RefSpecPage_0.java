	/**
	 * Create specifications selection page for provided context.
	 *
	 * @param local
	 *            local repository.
	 * @param pushPage
	 *            true if this page is used for push specifications selection,
	 *            false if it used for fetch specifications selection.
	 * @param repoPage
	 *            repository selection page - must be predecessor of this page
	 *            in wizard.
	 */
	public RefSpecPage(final Repository local, final boolean pushPage,
			final RepositorySelectionPage repoPage) {
		this(local, pushPage, repoPage, null);
	}

