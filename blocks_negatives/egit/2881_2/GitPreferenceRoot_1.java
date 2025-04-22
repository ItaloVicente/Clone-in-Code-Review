		Group initialGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		initialGroup.setText(UIText.GitPreferenceRoot_InitialConfiguration);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(initialGroup);
		addField(new BooleanFieldEditor(
				UIPreferences.SHOW_INITIAL_CONFIG_DIALOG,
				UIText.GitPreferenceRoot_ShowInitialConfigDialogCheckbox,
				initialGroup));
		updateMargins(initialGroup);

