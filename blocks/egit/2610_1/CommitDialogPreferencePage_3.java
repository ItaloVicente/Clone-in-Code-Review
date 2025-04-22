
		BooleanFieldEditor signedOffBy = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_SIGNED_OFF_BY,
				UIText.CommitDialogPreferencePage_signedOffBy,
				footersGroup);
		signedOffBy
				.getDescriptionControl(footersGroup)
				.setToolTipText(
						UIText.CommitDialogPreferencePage_signedOffByTooltip);
		addField(signedOffBy);
