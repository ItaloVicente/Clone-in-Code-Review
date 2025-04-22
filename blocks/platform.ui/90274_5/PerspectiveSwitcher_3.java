		IPreferenceStore apiPreferenceStore = PrefUtil.getAPIPreferenceStore();
		String showTextOnPerspectiveBarPreference = IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR;
		showtextMenuItem.addSelectionListener(widgetSelectedAdapter(e -> {
			boolean preference = showtextMenuItem.getSelection();
			if (preference != apiPreferenceStore.getDefaultBoolean(showTextOnPerspectiveBarPreference)) {
				PrefUtil.getInternalPreferenceStore().setValue(IPreferenceConstants.OVERRIDE_PRESENTATION, true);
