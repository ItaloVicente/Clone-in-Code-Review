
		BooleanFieldEditor checkBeforeCommitting = new BooleanFieldEditor(
				UIPreferences.CHECK_BEFORE_COMMITTING,
				UIText.CommittingPreferencePage_CheckBeforeCommitting, main);
		checkBeforeCommitting.getDescriptionControl(main).setToolTipText(
				UIText.CommittingPreferencePage_CheckBeforeCommittingTooltip);
		addField(checkBeforeCommitting);
