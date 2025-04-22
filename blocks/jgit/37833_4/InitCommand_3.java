	public InitCommand setGitDir(File gitDir)
			throws IllegalStateException {
		validateDirs(directory
		this.gitDir = gitDir;
		return this;
	}

	private static void validateDirs(File directory
			throws IllegalStateException {
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

