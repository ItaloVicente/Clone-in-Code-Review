			config.addChangeListener(new ConfigChangedListener() {

				public void onConfigChanged(ConfigChangedEvent event) {
					repo.getListenerList().dispatch(new ConfigChangedEvent());
				}
			});
		} else {
			config = repo.getConfig();
