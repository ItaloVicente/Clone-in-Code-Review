		myConfigChangeListener = new ConfigChangedListener() {
			public void onConfigChanged(ConfigChangedEvent event) {
				lastRepositoryChange = System.currentTimeMillis();
				scheduleRefresh(DEFAULT_REFRESH_DELAY);
			}
		};

