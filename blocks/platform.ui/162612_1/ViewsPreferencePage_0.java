	protected void createUseSquareTabs(Composite composite) {
		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		useSquareTabs = createCheckButton(composite, WorkbenchMessages.ViewsPreference_useSquareTabs,
				apiStore.getBoolean(IWorkbenchPreferenceConstants.USE_SQUARE_TABS));
	}

