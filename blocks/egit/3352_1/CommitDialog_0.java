		Link preferencesLink = new Link(optionsContainer, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.FILL).applyTo(preferencesLink);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.internal.preferences.CommitDialogPreferencePage"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(), preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
				commitText.reconfigure();
			}
		});

		signedOffButton = new Button(optionsContainer, SWT.CHECK);
