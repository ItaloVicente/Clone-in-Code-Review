		BooleanFieldEditor createChangeId = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_CREATE_CHANGE_ID,
				UIText.CommitDialogPreferencePage_createChangeId,
				footersGroup);
		createChangeId
				.getDescriptionControl(footersGroup)
				.setToolTipText(
						UIText.CommitDialogPreferencePage_createChangeIdTooltip);
		addField(createChangeId);

