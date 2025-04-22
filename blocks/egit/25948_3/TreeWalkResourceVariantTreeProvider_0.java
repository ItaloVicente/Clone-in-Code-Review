	private boolean equalModes(int modeBase, int modeOurs, int modeTheirs) {
		FileMode mode = FileMode.fromBits(modeBase);
		if (mode == FileMode.MISSING)
			mode = FileMode.fromBits(modeOurs);
		else if (modeOurs != 0 && mode != FileMode.fromBits(modeOurs))
			return false;
		return modeTheirs == 0 || mode == FileMode.fromBits(modeTheirs);
	}

