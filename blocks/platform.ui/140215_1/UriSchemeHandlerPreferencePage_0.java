		if (operatingSystemRegistration == null) {
			setErrorMessage(NLS.bind(UrlHandlerPreferencePage_UnsupportedOperatingSystem,
					Platform.isRunning() ? Platform.getOS() : null)); // running check for plain JUnit tests
			tableViewer.getControl().setEnabled(false);
		} else if (currentLocation == null) {
			setErrorMessage(UrlHandlerPreferencePage_LauncherCannotBeDetermined);
			tableViewer.getControl().setEnabled(false);
		}
