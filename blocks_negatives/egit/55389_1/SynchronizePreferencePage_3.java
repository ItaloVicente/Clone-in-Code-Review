		Composite modelStrategyParent = getFieldEditorParent();
		preferredMergeStrategyEditor = new RadioGroupFieldEditor(
				GitCorePreferences.core_preferredMergeStrategy,
				UIText.GitPreferenceRoot_preferreMergeStrategy_group, 1,
				getAvailableMergeStrategies(), modelStrategyParent, false);
		preferredMergeStrategyEditor.getLabelControl(modelStrategyParent)
				.setToolTipText(
						UIText.GitPreferenceRoot_preferreMergeStrategy_label);
		addField(preferredMergeStrategyEditor);
