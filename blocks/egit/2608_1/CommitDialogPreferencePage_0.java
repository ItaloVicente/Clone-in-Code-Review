
		BooleanFieldEditor createChangeId = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_CREATE_CHANGE_ID,
				UIText.CommitDialogPreferencePage_createChangeId,
				getFieldEditorParent());
		createChangeId
				.getDescriptionControl(getFieldEditorParent())
				.setToolTipText(
						UIText.CommitDialogPreferencePage_createChangeIdTooltip);
		addField(createChangeId);
