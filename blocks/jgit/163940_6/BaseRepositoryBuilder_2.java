			} else {
				setGitDir(safeFS().getSymRef(getWorkTree()
			}
		}
		if (getGitCommonDir() == null) {
			setGitCommonDir(safeFS().getCommonDir(getGitDir()));
