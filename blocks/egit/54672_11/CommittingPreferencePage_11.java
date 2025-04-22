
		warnCheckbox = createCheckBox(main,
				UIText.CommittingPreferencePage_CheckBeforeCommitting);

		((GridData) warnCheckbox.getLayoutData()).horizontalSpan = 2;
		warnCheckbox.setSelection(doGetPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING));

		warnCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleWarnCheckboxSelection(warnCheckbox.getSelection());
			}
		});

		warningGroup = createGroup(main, 2);
		warningGroup.setLayout(new GridLayout(2, false));

		warnCombo = new ComboFieldEditor(UIPreferences.WARN_BEFORE_COMMITTING_LEVEL,
				UIText.CommittingPreferencePage_WarnBeforeCommitting,
				new String[][] {
						{ UIText.CommittingPreferencePage_WarnBlock_Errors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_ERRORS },
						{ UIText.CommittingPreferencePage_WarnBlock_WarningsAndErrors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_WARNINGS_AND_ERRORS } },
				warningGroup);
		addField(warnCombo);
		blockCheckbox = createCheckBox(warningGroup,
				UIText.CommittingPreferencePage_BlockCommit);

		((GridData) blockCheckbox.getLayoutData()).horizontalSpan = 2;
		blockCheckbox.setSelection(
				doGetPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT));

		blockCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBlockCheckboxSelection(blockCheckbox.getSelection());
			}
		});
		blockGroup = createGroup(warningGroup, 2);
		blockGroup.setLayout(new GridLayout(2, false));
		blockCombo = new ComboFieldEditor(UIPreferences.BLOCK_COMMIT_LEVEL,
				UIText.CommittingPreferencePage_BlockCommitCombo,
				new String[][] {
						{ UIText.CommittingPreferencePage_WarnBlock_Errors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_ERRORS },
						{ UIText.CommittingPreferencePage_WarnBlock_WarningsAndErrors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_WARNINGS_AND_ERRORS } },
				blockGroup);
		addField(blockCombo);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 3)
				.applyTo(warningGroup);
		GridLayoutFactory.fillDefaults().numColumns(2).applyTo(warningGroup);
		updateMargins(warningGroup);
		updateMargins(blockGroup);
		handleWarnCheckboxSelection(warnCheckbox.getSelection());
		handleBlockCheckboxSelection(blockCheckbox.getSelection());
