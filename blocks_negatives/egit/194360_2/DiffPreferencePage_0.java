
		Button useExternal = new Button(diffGroup, SWT.RADIO);
		useExternal.setText(UIText.DiffPreferencePage_UseExternal);
		if (prefsManager.isActiveMode(DiffToolMode.EXTERNAL)) {
			useExternal.setSelection(true);
		}

		diffControls.put(useExternal, DiffToolMode.EXTERNAL);


		Composite diffToolCustomCont = new Composite(diffGroup, SWT.None);
		diffToolCustomCont.setLayout(createGridWithLeftMergins());

		Combo customDiffCombo = new Combo(diffToolCustomCont, SWT.READ_ONLY);
		Set<String> diffToolsList = DiffMergeSettings.getAvailableDiffTools();

		for (String tool : diffToolsList) {
			customDiffCombo.add(tool);
		}

		useExternal.addListener(SWT.Selection, event -> {
			if (useExternal.getSelection()) {
				prefsManager.setActiveMode(DiffToolMode.EXTERNAL);
				useExternalForType.setEnabled(false);
				prefsManager.setCustomTool(DIFF_TOOL_CUSTOM,
						customDiffCombo.getText());
			}
		});

		IPreferenceStore store = getPreferenceStore();
		String defaultCustomDiffTool = store.getString(DIFF_TOOL_CUSTOM);
		if (diffToolsList.contains(defaultCustomDiffTool)) {
			customDiffCombo.setText(defaultCustomDiffTool);
		} else {
			customDiffCombo
		}
		customDiffCombo
				.setEnabled(prefsManager.isActiveMode(DiffToolMode.EXTERNAL));

		customDiffCombo.addListener(SWT.Selection, event -> {
			prefsManager.setCustomTool(DIFF_TOOL_CUSTOM,
					customDiffCombo.getText());
		});

		useExternal.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				customDiffCombo.setEnabled(useExternal.getSelection());
			}
		});
		prefsManager.addControlWithCustomReset(customDiffCombo, () -> {
			customDiffCombo.setEnabled(false);
			customDiffCombo.setText(store.getString(DIFF_TOOL_CUSTOM));
		});

