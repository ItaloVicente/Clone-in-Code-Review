	@Override
	public void init(IWorkbench workbench) {
		if (operatingSystemRegistration == null) {
			operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
		}
		if (extensionReader == null) {
			extensionReader = IUriSchemeExtensionReader.getInstance();
		}
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
		}
	}

