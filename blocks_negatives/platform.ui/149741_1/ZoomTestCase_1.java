	@Override
	protected void doTearDown() throws Exception {

		super.doTearDown();

		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		apiStore.setValue(IWorkbenchPreferenceConstants.ENABLE_NEW_MIN_MAX, oldMinMaxState);
	}

