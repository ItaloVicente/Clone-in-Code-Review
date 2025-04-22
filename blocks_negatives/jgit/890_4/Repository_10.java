	public File getIndexFile() throws IllegalStateException {
		if (isBare())
			throw new IllegalStateException(
					JGitText.get().bareRepositoryNoWorkdirAndIndex);
		return indexFile;
	}
