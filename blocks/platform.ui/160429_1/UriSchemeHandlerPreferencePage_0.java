
		if (operatingSystemRegistration == null) {
			operatingSystemRegistration = IOperatingSystemRegistration.getInstance(listener);
		}
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
		}

