		preferenceListener = new IPreferenceChangeListener() {
			@Override
			public void preferenceChange(PreferenceChangeEvent event) {
				if (ignoreEvents) {
					return;
				}
