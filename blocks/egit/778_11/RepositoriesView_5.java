	public RepositoriesView() {
		repositoryUtil = Activator.getDefault().getRepositoryUtil();
		repositoryCache = org.eclipse.egit.core.Activator.getDefault()
				.getRepositoryCache();

		configurationListener = new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				lastInputChange = System.currentTimeMillis();
				scheduleRefresh();
