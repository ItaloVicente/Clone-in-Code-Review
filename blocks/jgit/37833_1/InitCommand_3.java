	public InitCommand setSeparateGitDir(File gitDir)
			throws IllegalStateException {
		this.gitDir = gitDir;
		validateDirs();
		return this;
	}

	private void validateDirs() throws IllegalStateException {
		if (directory != null) {
			if (bare) {
				if (gitDir != null && !gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedBareRepoDifferentDirs
							gitDir
			} else {
				if (gitDir != null && gitDir.equals(directory))
					throw new IllegalStateException(MessageFormat.format(
							JGitText.get().initFailedNonBareRepoSameDirs
							gitDir
			}
		}
	}

