
		ObjectChecker newObjectChecker() {
			if (!checkReceivedObjects)
				return null;
			return new ObjectChecker()
				.setAllowLeadingZeroFileMode(allowLeadingZeroFileMode)
				.setAllowInvalidPersonIdent(allowInvalidPersonIdent)
				.setSafeForWindows(safeForWindows)
				.setSafeForMacOS(safeForMacOS);
		}
