	public B setGitCommonDir(File gitCommonDir) {
		this.gitCommonDir = gitCommonDir;
		this.config = null;
		return self();
	}

	public File getGitCommonDir() {
		return gitCommonDir;
	}

