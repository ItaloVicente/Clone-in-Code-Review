		Link committingPreferencesLink = new Link(container, SWT.NONE);
		committingPreferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		committingPreferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.internal.preferences.CommittingPreferencePage"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(null, preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
			}
		});

