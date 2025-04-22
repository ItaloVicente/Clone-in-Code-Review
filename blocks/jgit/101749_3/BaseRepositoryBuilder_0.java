	public B setGitDir(File gitDir) throws IOException {
		if (gitDir == null || !gitDir.isFile()) {
			this.gitDir = gitDir;
		} else {
			File workTree = gitDir.getParentFile();
			this.gitDir = getSymRef(workTree
		}
