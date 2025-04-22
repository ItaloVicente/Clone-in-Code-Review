    	dialogSettings = createEmptySettings();
		boolean loaded = loadDialogSettingsFromWorkspace();
		if (!loaded) {
			loadDefaultDialogSettingsFromBundle();
		}
	}
