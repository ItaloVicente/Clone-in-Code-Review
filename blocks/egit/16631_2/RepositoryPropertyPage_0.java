			config.addChangeListener(new ConfigChangedListener() {

				public void onConfigChanged(ConfigChangedEvent event) {
					repo.fireEvent(new ConfigChangedEvent());
				}
			});
