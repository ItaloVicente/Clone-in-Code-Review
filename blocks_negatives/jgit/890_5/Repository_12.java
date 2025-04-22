	public File getWorkDir() throws IllegalStateException {
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		return workDir;
	}
