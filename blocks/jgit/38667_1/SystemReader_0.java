	protected final void setPlatformChecker() {
		platformChecker = new ObjectChecker()
			.setSafeForWindows(isWindows())
			.setSafeForMacOS(isMacOS());
	}

