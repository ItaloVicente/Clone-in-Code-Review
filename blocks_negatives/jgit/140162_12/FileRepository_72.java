		repoConfig.addChangeListener(new ConfigChangedListener() {
			@Override
			public void onConfigChanged(ConfigChangedEvent event) {
				fireEvent(event);
			}
		});
