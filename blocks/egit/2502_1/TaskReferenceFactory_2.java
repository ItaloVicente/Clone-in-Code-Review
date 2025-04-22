	private static String getConfiguredUrl(Repository repo) {
		return repo.getConfig().getString(BUGTRACK_SECTION, null, BUGTRACK_URL);
	}

	private static String getOriginUrl(Repository repo) {
		return repo.getConfig().getString("remote", "origin", "url");  //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
