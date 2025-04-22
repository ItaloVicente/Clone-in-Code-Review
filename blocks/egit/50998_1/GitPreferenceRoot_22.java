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
