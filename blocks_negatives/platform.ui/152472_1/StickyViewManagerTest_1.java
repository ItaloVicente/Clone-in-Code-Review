	@Override
	protected void doTearDown() throws Exception {
		super.doTearDown();
		PlatformUI.getPreferenceStore().setValue(
				IWorkbenchPreferenceConstants.ENABLE_32_STICKY_CLOSE_BEHAVIOR,
				originalPreference);
	}

