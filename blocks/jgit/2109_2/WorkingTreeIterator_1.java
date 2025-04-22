
		if (modeDiff != 0 && !FileMode.SYMLINK.equals(entry.getRawMode())) {
			if (!state.options.isFileMode())
				modeDiff &= ~FileMode.EXECUTABLE_FILE.getBits();
			if (modeDiff != 0)
				return true;
		}
