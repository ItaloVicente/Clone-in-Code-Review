		skipHiddenButton = new Button(searchGroup, SWT.CHECK);
		skipHiddenButton.setLayoutData(
				new GridData(SWT.LEFT, SWT.CENTER, false, false, 4, 1));
		skipHiddenButton
				.setSelection(prefs.getBoolean(PREF_SKIP_HIDDEN, true));
		skipHiddenButton.setText(UIText.RepositorySearchDialog_SkipHidden);
		skipHiddenButton.setToolTipText(
				UIText.RepositorySearchDialog_SkipHiddenTooltip);

		skipHiddenButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				prefs.putBoolean(PREF_SKIP_HIDDEN, skipHiddenButton.getSelection());
				try {
					prefs.flush();
				} catch (BackingStoreException e1) {
				}
				setNeedsSearch();
			}
		});

