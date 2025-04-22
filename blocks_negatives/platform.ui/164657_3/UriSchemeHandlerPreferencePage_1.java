		if (operatingSystemRegistration == null) {
			setErrorMessage(NLS.bind(UrlHandlerPreferencePage_UnsupportedOperatingSystem,
			setDataOnTableViewer(Collections.emptyList());

		} else if (currentLocation == null) {
			setErrorMessage(UrlHandlerPreferencePage_LauncherCannotBeDetermined);
