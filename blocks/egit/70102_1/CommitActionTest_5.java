	@After
	public void tearDown() {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.ALWAYS_USE_STAGING_VIEW, true);
	}

