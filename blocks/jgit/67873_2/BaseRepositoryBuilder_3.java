		if (getObjectDirectory() == null) {
			if (getGitCommonDir() != null) {
				setObjectDirectory(
						safeFS().resolve(getGitCommonDir()
			} else if (getGitDir() != null) {
				setObjectDirectory(safeFS().resolve(getGitDir()
			}
		}
