	protected void createUseRoundTabs(Composite composite) {
		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();
		useRoundTabs = createCheckButton(composite, WorkbenchMessages.ViewsPreference_useRoundTabs,
				apiStore.getBoolean(IWorkbenchPreferenceConstants.USE_ROUND_TABS));
	}

