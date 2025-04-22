		pushSettings = new PushSettings();
		Control pushSettingsUi = pushSettings
				.createControl(commitButtonsContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(pushSettingsUi);

		commitAndPushButton = toolkit.createButton(commitButtonsContainer,
