		Link preferencesLink = new Link(container, SWT.NONE);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.internal.preferences.CommitDialogPreferencePage"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(null, preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
				commitText.reconfigure();
			}
		});

