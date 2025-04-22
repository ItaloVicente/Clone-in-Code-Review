	private boolean hasSignificantDifference(int modeBase, int modeOurs,
			int modeTheirs) {
		if (modeBase == 0) {
			if (FileMode.fromBits(modeOurs | modeTheirs) != FileMode.MISSING) {
				return true;
			} else {
				return (FileMode.fromBits(modeOurs) == FileMode.TREE && FileMode
						.fromBits(modeTheirs) != FileMode.TREE)
						|| (FileMode.fromBits(modeOurs) != FileMode.TREE && FileMode
								.fromBits(modeTheirs) == FileMode.TREE);
			}
		}
		return FileMode.fromBits(modeBase & modeOurs) != FileMode.MISSING
				|| FileMode.fromBits(modeBase & modeTheirs) != FileMode.MISSING;
	}

