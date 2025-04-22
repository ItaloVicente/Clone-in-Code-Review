	private void createColoredIconsPref(Composite composite) {
		IPreferenceStore apiStore = PrefUtil.getInternalPreferenceStore();

		useColoredIcons = createCheckButton(composite, WorkbenchMessages.ViewsPreference_useColoredIcons,
				apiStore.getBoolean(IPreferenceConstants.COLOR_ICONS));
	}

