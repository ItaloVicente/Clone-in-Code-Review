		ToolBarManager settingsManager = new ToolBarManager(
				SWT.FLAT | SWT.HORIZONTAL);
		pushSettings = new PushSettings();
		settingsManager.add(pushSettings);
		pushSettingsBar = settingsManager.createControl(commitButtonsContainer);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER)
				.applyTo(pushSettingsBar);

		commitAndPushButton = toolkit.createButton(commitButtonsContainer,
