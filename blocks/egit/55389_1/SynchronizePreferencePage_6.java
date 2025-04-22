		helper.createPreferredStrategyPanel(getFieldEditorParent());

		addField(new BooleanFieldEditor(
				UIPreferences.PREFERRED_MERGE_STRATEGY_HIDE_DIALOG,
				UIText.GitPreferenceRoot_hideMergeStrategyDialog,
				getFieldEditorParent()));
