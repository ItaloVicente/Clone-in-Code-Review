	public boolean isModeDifferent(final int rawMode) {
		int modeDiff = getEntryRawMode() ^ rawMode;

		if (modeDiff == 0)
			return false;

		if (FileMode.SYMLINK.equals(rawMode))
			return false;

		if (!state.options.isFileMode())
			modeDiff &= ~FileMode.EXECUTABLE_FILE.getBits();
		return modeDiff != 0;
	}

