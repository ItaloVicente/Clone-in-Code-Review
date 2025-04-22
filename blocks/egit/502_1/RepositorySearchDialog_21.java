					prefs.put(PREF_PATH, directory);
					try {
						prefs.flush();
					} catch (BackingStoreException e1) {
					}
				}
			}

		});

		new Label(main, SWT.NONE);

		final Button btnLookForNested = new Button(main, SWT.CHECK);
		btnLookForNested.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER,
				false, false, 2, 1));
		btnLookForNested
				.setSelection(prefs.getBoolean(PREF_DEEP_SEARCH, false));
		btnLookForNested
				.setText(UIText.RepositorySearchDialog_DeepSearch_button);

		btnLookForNested.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				prefs.putBoolean(PREF_DEEP_SEARCH, btnLookForNested
						.getSelection());
				try {
					prefs.flush();
				} catch (BackingStoreException e1) {
