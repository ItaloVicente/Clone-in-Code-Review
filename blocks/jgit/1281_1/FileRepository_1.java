		configChangeListener = new ChangeListener() {
			public void onChange(EventObject event) {
				fireEvent(new ConfigChangedEvent());
			}
		};

		getConfig().addChangeListener(configChangeListener);

