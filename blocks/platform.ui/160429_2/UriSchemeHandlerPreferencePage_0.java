
		boolean startJob = false;
		if (operatingSystemRegistration == null) {
			operatingSystemRegistration = IOperatingSystemRegistration.getInstance();
			startJob = true;
		}
		if (operatingSystemRegistration != null) {
			currentLocation = operatingSystemRegistration.getEclipseLauncher();
			if (currentLocation != null && startJob) {
				startRegistrationReadingJob();
			}
		}

