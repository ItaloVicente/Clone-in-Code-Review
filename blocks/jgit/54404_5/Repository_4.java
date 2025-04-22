		return getGitDir(false);
	}

	public File getGitDir(boolean preferCommonDir) {
		return preferCommonDir && gitCommonDir != null ? gitCommonDir : gitDir;
	}

	public File getGitCommonDir() {
		return gitCommonDir;
