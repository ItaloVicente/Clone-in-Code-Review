				confirmDialogsGroup));
		updateMargins(confirmDialogsGroup);

		Group warningsGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(warningsGroup);
		warningsGroup
				.setText(UIText.DialogsPreferencePage_HideWarningGroupHeader);

		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(warningsGroup);
		addField(new BooleanFieldEditor(UIPreferences.SHOW_HOME_DIR_WARNING,
				UIText.DialogsPreferencePage_HomeDirWarning, warningsGroup));
		addField(new BooleanFieldEditor(UIPreferences.SHOW_GIT_PREFIX_WARNING,
				UIText.DialogsPreferencePage_GitPrefixWarning, warningsGroup));
		updateMargins(warningsGroup);
