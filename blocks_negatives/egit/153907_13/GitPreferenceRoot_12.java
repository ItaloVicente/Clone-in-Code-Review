		loadUserScopedConfig();

		Group mergeGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(mergeGroup);
		mergeGroup.setText(UIText.GitPreferenceRoot_MergeGroupHeader);
		ComboFieldEditor mergeMode = new ComboFieldEditor(
				UIPreferences.MERGE_MODE,
				UIText.GitPreferenceRoot_MergeModeLabel,
				MERGE_MODE_NAMES_AND_VALUES, mergeGroup);
		mergeMode.getLabelControl(mergeGroup).setToolTipText(
				UIText.GitPreferenceRoot_MergeModeTooltip);
		addField(mergeMode);
		ComboFieldEditor mergeTool = new ComboFieldEditor(
				UIPreferences.MERGE_TOOL,
				UIText.GitPreferenceRoot_MergeToolLabel,
				MERGE_TOOL_NAMES_AND_VALUES, mergeGroup);
		mergeTool.getLabelControl(mergeGroup)
				.setToolTipText(UIText.GitPreferenceRoot_MergeToolTooltip);
		addField(mergeTool);
		ComboFieldEditor mergeToolCustom = new ComboFieldEditor(
				UIPreferences.MERGE_TOOL_CUSTOM,
				UIText.GitPreferenceRoot_MergeToolCustomLabel, mergeToolsList,
				mergeGroup);
		mergeToolCustom.getLabelControl(mergeGroup).setToolTipText(
				UIText.GitPreferenceRoot_MergeToolCustomTooltip);
		addField(mergeToolCustom);
		BooleanFieldEditor autoAddToIndex = new BooleanFieldEditor(
				UIPreferences.MERGE_TOOL_AUTO_ADD_TO_INDEX,
				UIText.GitPreferenceRoot_MergeToolAutoAddLabel, mergeGroup);
		addField(autoAddToIndex);
		updateMargins(mergeGroup);

		Group diffGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(diffGroup);
		diffGroup.setText(UIText.GitPreferenceRoot_DiffGroupHeader);
		ComboFieldEditor diffTool = new ComboFieldEditor(
				UIPreferences.DIFF_TOOL, UIText.GitPreferenceRoot_DiffToolLabel,
				DIFF_TOOL_NAMES_AND_VALUES, diffGroup);
		diffTool.getLabelControl(diffGroup)
				.setToolTipText(UIText.GitPreferenceRoot_DiffToolTooltip);
		addField(diffTool);
		ComboFieldEditor diffToolCustom = new ComboFieldEditor(
				UIPreferences.DIFF_TOOL_CUSTOM,
				UIText.GitPreferenceRoot_DiffToolCustomLabel, diffToolsList,
				diffGroup);
		diffToolCustom.getLabelControl(diffGroup)
				.setToolTipText(UIText.GitPreferenceRoot_DiffToolCustomTooltip);
		addField(diffToolCustom);
		updateMargins(diffGroup);

