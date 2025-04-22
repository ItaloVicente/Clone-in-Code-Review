		showtextMenuItem.addSelectionListener(widgetSelectedAdapter(e -> {
			boolean preference = showtextMenuItem.getSelection();
			if (preference != PrefUtil.getAPIPreferenceStore().getDefaultBoolean(
					IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR)) {
				PrefUtil.getInternalPreferenceStore().setValue(
						IPreferenceConstants.OVERRIDE_PRESENTATION, true);
