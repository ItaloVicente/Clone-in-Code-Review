		if (platformChecker == null)
			setPlatformChecker();
	}

	protected final void setPlatformChecker() {
		platformChecker = new ObjectChecker()
			.setSafeForWindows(isWindows())
			.setSafeForMacOS(isMacOS());
