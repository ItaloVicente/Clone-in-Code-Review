	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();
		apiPreferenceStore = PrefUtil.getAPIPreferenceStore();

		originalShowOpenValue = apiPreferenceStore
				.getBoolean(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR);
		originalPerspectiveBarPosition = apiPreferenceStore
				.getString(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR);
	}

	@Override
	protected void doTearDown() throws Exception {
		apiPreferenceStore.setValue(IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR, originalShowOpenValue);
		apiPreferenceStore.setValue(IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR, originalPerspectiveBarPosition);

		super.doTearDown();
	}

