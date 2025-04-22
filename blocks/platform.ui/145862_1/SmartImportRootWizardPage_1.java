	private void loadWidgetStates() {
		IDialogSettings dialogSettings = getDialogSettings();

		if (dialogSettings != null && dialogSettings.get(STORE_HIDE_ALREADY_OPEN) != null) {
			hideAlreadyOpen = dialogSettings.getBoolean(STORE_HIDE_ALREADY_OPEN);
			closeProjectsAfterImport = dialogSettings.getBoolean(STORE_CLOSE_IMPORTED);
			detectNestedProjects = dialogSettings.getBoolean(STORE_NESTED_PROJECTS);
			configureProjects = dialogSettings.getBoolean(STORE_CONFIGURE_NATURES);
		}
	}

	private void saveWidgetStates() {
		IDialogSettings dialogSettings = getDialogSettings();
		if (dialogSettings != null) {
			dialogSettings.put(STORE_HIDE_ALREADY_OPEN, hideAlreadyOpen);
			dialogSettings.put(STORE_CLOSE_IMPORTED, closeProjectsAfterImport);
			dialogSettings.put(STORE_NESTED_PROJECTS, detectNestedProjects);
			dialogSettings.put(STORE_CONFIGURE_NATURES, configureProjects);
		}
	}

