	@After
	public void tearDown() {
		org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.setToDefault(UIPreferences.USE_LOGICAL_MODEL);
	}

