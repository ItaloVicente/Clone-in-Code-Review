		Group pushGroup = new Group(main, SWT.SHADOW_ETCHED_IN);
		pushGroup.setText(UIText.CommittingPreferencePage_push);
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1)
				.applyTo(pushGroup);

		BooleanFieldEditor alwaysShowPushWizard = new BooleanFieldEditor(
				UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT,
				UIText.CommittingPreferencePage_alwaysShowPushWizard,
				pushGroup);
		alwaysShowPushWizard.getDescriptionControl(pushGroup).setToolTipText(
				UIText.CommittingPreferencePage_alwaysShowPushWizardTooltip);
		addField(alwaysShowPushWizard);
		updateMargins(pushGroup);

