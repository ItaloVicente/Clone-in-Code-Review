		}
	}

	private ObjectChecker platformChecker;

	private void init() {
		if (platformChecker == null) {
			platformChecker = new ObjectChecker()
				.setSafeForWindows(isWindows())
				.setSafeForMacOS(isMacOS());
		}
