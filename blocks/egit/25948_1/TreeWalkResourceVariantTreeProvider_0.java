	private boolean equalModes(int modeBase, int modeOurs, int modeTheirs) {
		FileMode mode = FileMode.fromBits(modeBase);
		if (mode == FileMode.MISSING)
			mode = FileMode.fromBits(modeOurs);
		else if (mode != FileMode.fromBits(modeOurs))
			return false;
		return mode == FileMode.MISSING
				|| mode == FileMode.fromBits(modeTheirs);
	}

