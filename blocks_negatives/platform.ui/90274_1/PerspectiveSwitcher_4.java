		showtextMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean preference = showtextMenuItem.getSelection();
				if (preference != PrefUtil.getAPIPreferenceStore().getDefaultBoolean(
						IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR)) {
					PrefUtil.getInternalPreferenceStore().setValue(
							IPreferenceConstants.OVERRIDE_PRESENTATION, true);
				}
				PrefUtil.getAPIPreferenceStore().setValue(
						IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR, preference);
				changeShowText(preference);
