
		configurationListener = new IPreferenceChangeListener() {
			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				if (RepositoryUtil.PREFS_DIRECTORIES_REL
						.equals(event.getKey())) {
					lastInputChange = System.currentTimeMillis();
					scheduleRefresh(DEFAULT_REFRESH_DELAY, null);
				}
			}
		};

		myRefsChangedListener = new RefsChangedListener() {
			@Override
			public void onRefsChanged(RefsChangedEvent e) {
				scheduleRefresh(DEFAULT_REFRESH_DELAY, null);
			}
		};

		myIndexChangedListener = new IndexChangedListener() {
			@Override
			public void onIndexChanged(IndexChangedEvent event) {
				scheduleRefresh(DEFAULT_REFRESH_DELAY, null);

			}
		};

		myConfigChangeListener = new ConfigChangedListener() {
			@Override
			public void onConfigChanged(ConfigChangedEvent event) {
				scheduleRefresh(DEFAULT_REFRESH_DELAY, null);
			}
		};
