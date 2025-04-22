		getConfig().addChangeListener(new ConfigChangedListener() {
			public void onConfigChanged(ConfigChangedEvent event) {
				fireEvent(event);
			}
		});

