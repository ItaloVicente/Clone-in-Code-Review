		ConfigChangedListener configChangeListener = new ConfigChangedListener() {
			public void onConfigChanged(ConfigChangedEvent event) {
				fireEvent(event);
			}
		};

		configChangeListenerHandle = getConfig().addChangeListener(configChangeListener);

