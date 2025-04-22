
		Link preferencesLink = new Link(headerArea, SWT.NONE);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.CENTER)
				.grab(true, false).applyTo(preferencesLink);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String preferencePageId = "org.eclipse.egit.ui.internal.preferences.CommitDialogPreferencePage"; //$NON-NLS-1$
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(), preferencePageId,
								new String[] { preferencePageId }, null);
				if (Window.OK == dialog.open())
					commitText.reconfigure();
			}
		});
