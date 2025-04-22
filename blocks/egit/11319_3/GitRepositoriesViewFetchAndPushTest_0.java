		oldShowPushConfirm = Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.SHOW_PUSH_CONFIRM);
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SHOW_PUSH_CONFIRM, true);
	}

	@After
	public void after() throws Exception {
		Activator.getDefault().getPreferenceStore()
				.setValue(UIPreferences.SHOW_PUSH_CONFIRM, oldShowPushConfirm);
