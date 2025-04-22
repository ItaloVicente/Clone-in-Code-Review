	private final IPreferenceChangeListener preferenceListener = new IPreferenceChangeListener() {

		@Override
		public void preferenceChange(PreferenceChangeEvent event) {
			if (!RepositoryUtil.PREFS_DIRECTORIES.equals(event.getKey())) {
				return;
			}
			prune(Activator.getDefault().getRepositoryUtil().getRepositories());
		}

	};

