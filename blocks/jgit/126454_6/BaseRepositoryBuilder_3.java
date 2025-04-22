		if (getObjectDirectory() == null) {
			File commonDir = getGitCommonDirFallback();
			if (commonDir != null) {
				setObjectDirectory(safeFS().resolve(commonDir
			}
		}
