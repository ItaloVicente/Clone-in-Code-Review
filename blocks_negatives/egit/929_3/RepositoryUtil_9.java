	/**
	 * Checks if the directories are still valid; in case of invalid directories
	 * the underlying {@link IEclipsePreferences} will be changed (by removing
	 * these directories) and the registered listeners will be notified.
	 */
	public void checkDirectories() {
		getConfiguredRepositories(true);
	}

