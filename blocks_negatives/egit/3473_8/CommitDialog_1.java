		committerHandler = UIUtils.addPreviousValuesContentProposalToText(committerText, COMMITTER_VALUES_PREF);

		Link preferencesLink = new Link(container, SWT.NONE);
		preferencesLink.setText(UIText.CommitDialog_ConfigureLink);
		preferencesLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PreferenceDialog dialog = PreferencesUtil
						.createPreferenceDialogOn(getShell(), preferencePageId,
								new String[] { preferencePageId }, null);
				dialog.open();
				commitText.reconfigure();
			}
		});
