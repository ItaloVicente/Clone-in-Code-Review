	private final IPreferenceChangeListener configuredRepositoriesListener = new IPreferenceChangeListener() {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (!RepositoryUtil.PREFS_DIRECTORIES.equals(event.getKey())) {
				return;
			}
			prune(Activator.getDefault().getRepositoryUtil().getRepositories());
		}

	};

	RepositoryCache() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.addPreferenceChangeListener(configuredRepositoriesListener);
	}

	void dispose() {
		InstanceScope.INSTANCE.getNode(Activator.getPluginId())
				.removePreferenceChangeListener(configuredRepositoriesListener);
	}

