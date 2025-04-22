
		Group confirmGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		GridDataFactory.fillDefaults().grab(true, false).span(GROUP_SPAN, 1)
				.applyTo(confirmGroup);
		confirmGroup.setText(UIText.GitPreferenceRoot_HideConfirmationGroup);
		addField(new BooleanFieldEditor(UIPreferences.REBASE_HIDE_CONFIRM,
				UIText.GitPreferenceRoot_HideRebaseConfirmationField,
				confirmGroup));
		updateMargins(confirmGroup);
