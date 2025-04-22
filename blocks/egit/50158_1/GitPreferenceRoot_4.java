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
				UIText.GitPreferenceRoot_DiffToolCustomLabel,
				getCustomDiffTools(), diffGroup);
		diffToolCustom.getLabelControl(diffGroup)
				.setToolTipText(UIText.GitPreferenceRoot_DiffToolCustomTooltip);
		addField(diffToolCustom);

		updateMargins(diffGroup);

