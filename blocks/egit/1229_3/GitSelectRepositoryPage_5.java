		configChangeListener = new IPreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				Display display = tv.getControl().getDisplay();
				display.asyncExec(new Runnable() {
					public void run() {
						refreshRepositoryList();
						checkPage();
					}
				});
			}
		};
		util.getPreferences().addPreferenceChangeListener(configChangeListener);

