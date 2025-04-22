		createSpace(pageComponent);

		topLabel = new Label(pageComponent, SWT.NONE);
		topLabel.setText(IDEWorkbenchMessages.LinkedResourcesPreference_explanation);
		data = new GridData();
		data.verticalAlignment = GridData.FILL;
		data.horizontalAlignment = GridData.FILL;
		topLabel.setLayoutData(data);
		topLabel.setFont(font);

		pathVariablesGroup.createContents(pageComponent);

		Preferences preferences = ResourcesPlugin.getPlugin()
				.getPluginPreferences();
		boolean enableLinking = !preferences
				.getBoolean(ResourcesPlugin.PREF_DISABLE_LINKING);
		enableLinkedResourcesButton.setSelection(enableLinking);
		updateWidgetState(enableLinking);
		return pageComponent;
	}
