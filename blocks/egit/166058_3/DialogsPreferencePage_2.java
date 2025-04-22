		BooleanFieldEditor editor = new BooleanFieldEditor(
				UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT,
				UIText.DialogsPreferencePage_alwaysShowPushWizard,
				confirmDialogsGroup);
		addField(editor);
		editor.getDescriptionControl(confirmDialogsGroup).setToolTipText(
				UIText.DialogsPreferencePage_alwaysShowPushWizardTooltip);

