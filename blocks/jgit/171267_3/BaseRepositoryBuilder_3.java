		if (getObjectDirectory() == null) {
			File commonDir = getGitCommonDir();
			if (commonDir != null) {
				setObjectDirectory(safeFS().resolve(commonDir
			}
		}
