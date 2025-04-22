			} else {
				setGitDir(safeFS().getSymRef(getWorkTree()
			}
		}
	}

	protected void setupCommonDir() throws IOException {
		if (getGitCommonDir() == null) {
			setGitCommonDir(safeFS().getCommonDir(getGitDir()));
