
		if (getInfoDirectory() == null && getGitDir() != null)
			setInfoDirectory(safeFS().resolve(getGitDir()

		if (getSparseCheckoutFile() == null) {
			setSparseCheckoutFile(
					new File(getInfoDirectory()
		}
