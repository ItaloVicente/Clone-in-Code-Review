	protected void createEnableAnimationsPref(Composite composite) {
		IPreferenceStore apiStore = PrefUtil.getAPIPreferenceStore();

		enableAnimations = createCheckButton(composite, WorkbenchMessages.ViewsPreference_enableAnimations,
				apiStore.getBoolean(IWorkbenchPreferenceConstants.ENABLE_ANIMATIONS));
	}
