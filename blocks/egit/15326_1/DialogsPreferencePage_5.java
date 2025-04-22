		Group commitWarningsErrorsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(commitWarningsErrorsGroup);
		commitWarningsErrorsGroup
				.setText(UIText.DialogsPreferencePage_WarningsErrorsWhileCommitting);

		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(commitWarningsErrorsGroup);
		addField(new ComboFieldEditor(UIPreferences.COMMIT_WITH_WARNINGS_SCOPE,
				UIText.DialogsPreferencePage_WhenCommittingWarnings_Scope,
				COMMIT_WITH_WARNINGS_SCOPE, commitWarningsErrorsGroup));
		addField(new ComboFieldEditor(
				UIPreferences.COMMIT_WITH_WARNINGS_ACTION,
				UIText.DialogsPreferencePage_WhenCommittingWarnings_Action,
				COMMIT_WITH_WARNINGS_ACTION, commitWarningsErrorsGroup));
		addField(new ComboFieldEditor(UIPreferences.COMMIT_WITH_ERRORS_SCOPE,
				UIText.DialogsPreferencePage_WhenCommittingErrors_Scope,
				COMMIT_WITH_ERRORS_SCOPE, commitWarningsErrorsGroup));
		addField(new ComboFieldEditor(UIPreferences.COMMIT_WITH_ERRORS_ACTION,
				UIText.DialogsPreferencePage_WhenCommittingErrors_Action,
				COMMIT_WITH_ERRORS_ACTION, commitWarningsErrorsGroup));
		updateMargins(commitWarningsErrorsGroup);

