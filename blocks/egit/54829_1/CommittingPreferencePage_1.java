
		BooleanFieldEditor secondLineCheck = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_CHECK_SECOND_LINE,
				UIText.CommittingPreferencePage_secondLineCheckMessage,
				formattingGroup);
		secondLineCheck.getDescriptionControl(formattingGroup).setToolTipText(
				UIText.CommittingPreferencePage_secondLineCheckTooltip);
		addField(secondLineCheck);

