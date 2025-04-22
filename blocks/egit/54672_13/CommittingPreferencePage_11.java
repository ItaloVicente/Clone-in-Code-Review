		buildProblemsGroup = createGroup(main, 1);
		buildProblemsGroup.setText(
				UIText.CommittingPreferencePage_WarnBeforeCommittingTitle);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(buildProblemsGroup);

		warnCheckbox = createCheckBox(buildProblemsGroup,
				UIText.CommittingPreferencePage_CheckBeforeCommitting);
		((GridData) warnCheckbox.getLayoutData()).horizontalSpan = 3;
		warnCheckbox.setSelection(doGetPreferenceStore()
				.getBoolean(UIPreferences.WARN_BEFORE_COMMITTING));
		warnCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleWarnCheckboxSelection(warnCheckbox.getSelection());
			}
		});

		warnCombo = new ComboFieldEditor(UIPreferences.WARN_BEFORE_COMMITTING_LEVEL,
				UIText.CommittingPreferencePage_WarnBeforeCommitting,
				new String[][] {
						{ UIText.CommittingPreferencePage_WarnBlock_Errors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_ERRORS },
						{ UIText.CommittingPreferencePage_WarnBlock_WarningsAndErrors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_WARNINGS_AND_ERRORS } },
				buildProblemsGroup);
		addField(warnCombo);

		blockCheckbox = createCheckBox(buildProblemsGroup,
				UIText.CommittingPreferencePage_BlockCommit);
		((GridData) blockCheckbox.getLayoutData()).horizontalSpan = 3;
		blockCheckbox.setSelection(
				doGetPreferenceStore().getBoolean(UIPreferences.BLOCK_COMMIT));
		blockCheckbox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBlockCheckboxSelection(blockCheckbox.getSelection());
			}
		});

		blockCombo = new ComboFieldEditor(UIPreferences.BLOCK_COMMIT_LEVEL,
				UIText.CommittingPreferencePage_BlockCommitCombo,
				new String[][] {
						{ UIText.CommittingPreferencePage_WarnBlock_Errors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_ERRORS },
						{ UIText.CommittingPreferencePage_WarnBlock_WarningsAndErrors,
								PluginPreferenceInitializer.COMMITTING_PREFERENCE_PAGE_WARN_BLOCK_WARNINGS_AND_ERRORS } },
				buildProblemsGroup);
		addField(blockCombo);

		handleWarnCheckboxSelection(warnCheckbox.getSelection());
		handleBlockCheckboxSelection(blockCheckbox.getSelection());
		updateMargins(buildProblemsGroup);
