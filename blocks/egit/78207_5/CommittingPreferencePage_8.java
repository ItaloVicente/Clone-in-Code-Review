		autoStage = new BooleanFieldEditor(UIPreferences.AUTO_STAGE_ON_COMMIT,
				UIText.CommittingPreferencePage_AutoStageOnCommit, main);
		GridDataFactory.fillDefaults().indent(20, 0)
				.applyTo(autoStage.getDescriptionControl(main));
		addField(autoStage);
		autoStage.setEnabled(getPreferenceStore()
				.getBoolean(UIPreferences.ALWAYS_USE_STAGING_VIEW), main);

