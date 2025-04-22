	@Override
	public void init(IWorkbench workbench) {
		operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
		}
	}

