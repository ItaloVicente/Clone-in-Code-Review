		db.getListenerList().addConfigChangedListener(
				new ConfigChangedListener() {
					@Override
					public void onConfigChanged(ConfigChangedEvent event) {
						events[0] = event;
					}
				});
