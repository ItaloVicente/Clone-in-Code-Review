		extensionReader = getUriSchemeExtensionReader();
		try {
			operatingSystemRegistration = getOperationgSystemRegistration();
			if (operatingSystemRegistration == null) {
				setErrorMessage(NLS.bind(UrlHandlerPreferencePage_UnsupportedOperatingSystem,
						Platform.isRunning() ? Platform.getOS() : null)); // running check for plain JUnit tests
				setDataOnTableViewer(Collections.emptyList());
			} else {
				currentLocation = operatingSystemRegistration.getEclipseLauncher();
				if (currentLocation == null) {
					setErrorMessage(UrlHandlerPreferencePage_LauncherCannotBeDetermined);
					setDataOnTableViewer(Collections.emptyList());
				} else {
					setDataOnTableViewer(getLoadingSchemeInformationList());
					startRegistrationReadingJob();
				}
			}
		} catch (CoreException e) {
			setErrorMessage(UrlHandlerPreferencePage_Error_Getting_OS_Registration);
