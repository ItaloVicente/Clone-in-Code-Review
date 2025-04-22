
		if (modeDiff != 0 && entry.getFileMode() != FileMode.SYMLINK) {
			if (!state.options.isFileMode())
				modeDiff &= ~FileMode.EXECUTABLE_FILE.getBits();
			if (modeDiff != 0)
				return true;
		}
