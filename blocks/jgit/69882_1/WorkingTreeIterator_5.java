		if (getOptions().isFileMode() && iMode != FileMode.GITLINK && iMode != FileMode.TREE) {
			return wtMode;
		}
		if (!getOptions().isFileMode()) {
			if (FileMode.REGULAR_FILE == wtMode
					&& FileMode.EXECUTABLE_FILE == iMode) {
				return iMode;
			}
			if (FileMode.EXECUTABLE_FILE == wtMode
					&& FileMode.REGULAR_FILE == iMode) {
				return iMode;
			}
		}
		if (FileMode.GITLINK == iMode
				&& FileMode.TREE == wtMode) {
