		return checkReceivedObjects;
	}

	public ObjectChecker newObjectChecker() {
		if (!checkReceivedObjects)
			return null;
		return new ObjectChecker()
			.setAllowLeadingZeroFileMode(allowLeadingZeroFileMode)
			.setSafeForWindows(safeForWindows)
			.setSafeForMacOS(safeForMacOS);
