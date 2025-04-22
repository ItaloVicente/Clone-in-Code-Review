	 *            given order in GUI, with {@value Constants#DEFAULT_REMOTE_NAME} as the
	 *            default choice. List may be null or empty - no remotes
	 *            configurations appear in this case. Note that the provided
	 *            list may be changed by this constructor.
	 */
	public RepositorySelectionPage(final boolean sourceSelection,
			final List<RemoteConfig> configuredRemotes) {
		this(sourceSelection, configuredRemotes, null);
	}

	/**
	 * Special case: the URI is set externally
	 *
	 * @param sourceSelection
	 *            true if dialog is used for source selection; false otherwise
	 *            (destination selection). This indicates appropriate text
	 *            messages.
	 * @param configuredRemotes
	 *            list of configured remotes that user may select as an
	 *            alternative to manual URI specification. Remotes appear in
