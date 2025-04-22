		ColorRegistry colorRegistry = JFaceResources.getColorRegistry();

		Color foreground = colorRegistry.get(JFacePreferences.INFORMATION_FOREGROUND_COLOR);
		if (foreground == null) {
			foreground = JFaceColors.getInformationViewerForegroundColor(fShell.getDisplay());
		}

		Color background = colorRegistry.get(JFacePreferences.INFORMATION_BACKGROUND_COLOR);
		if (background == null) {
			background = JFaceColors.getInformationViewerBackgroundColor(fShell.getDisplay());
		}

		setForegroundColor(foreground);
		setBackgroundColor(background);
