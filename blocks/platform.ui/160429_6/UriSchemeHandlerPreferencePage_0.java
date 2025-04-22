
		if (operatingSystemRegistration == null) {
			operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
		}
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
			if (currentLocation != null) {
				startRegistrationReadingJob();
			}
		}

