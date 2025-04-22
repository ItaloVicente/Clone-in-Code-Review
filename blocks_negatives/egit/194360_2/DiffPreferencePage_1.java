
		Button mergeUseExternalTool = new Button(toolToUseSection, SWT.RADIO);
		mergeUseExternalTool.setText(UIText.DiffPreferencePage_UseExternal);
		if (prefsManager.isActiveMode(MergeToolMode.EXTERNAL)) {
			mergeUseExternalTool.setSelection(true);
		}
		mergeControls.put(mergeUseExternalTool, MergeToolMode.EXTERNAL);

		Composite mergeToolCustomCont = new Composite(toolToUseSection, SWT.None);
		mergeToolCustomCont.setLayout(new GridLayout());

		Combo customMergeCombo = new Combo(mergeToolCustomCont, SWT.READ_ONLY);
		Set<String> mergeTools = DiffMergeSettings.getAvailableMergeTools();
		for (String tool : mergeTools) {
			customMergeCombo.add(tool);
		}

		mergeUseExternalTool.addListener(SWT.Selection, event -> {
			prefsManager.setActiveMode(MergeToolMode.EXTERNAL);
			prefsManager.setCustomTool(MERGE_TOOL_CUSTOM,
					customMergeCombo.getText());
		});

		IPreferenceStore store = getPreferenceStore();
		String defaultCustomMergeTool = store.getString(MERGE_TOOL_CUSTOM);
		if (mergeTools.contains(defaultCustomMergeTool)) {
			customMergeCombo.setText(defaultCustomMergeTool);
		} else {
			customMergeCombo
		}
		if (prefsManager.isActiveMode(MergeToolMode.INTERNAL)) {
			customMergeCombo.setEnabled(false);
		} else {
			customMergeCombo.setEnabled(true);
		}
		mergeUseExternalTool.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				customMergeCombo.setEnabled(mergeUseExternalTool.getSelection());
				useExternalForType.setEnabled(false);
			}
		});
		customMergeCombo.addListener(SWT.Selection, event -> {
			prefsManager.setCustomTool(MERGE_TOOL_CUSTOM,
					customMergeCombo.getText());
		});

		prefsManager.addControlWithCustomReset(customMergeCombo, () -> {
			customMergeCombo.setEnabled(false);
			customMergeCombo.setText(store.getString(MERGE_TOOL_CUSTOM));
		});

