	protected abstract File discoverGitExe();

	protected File discoverGitSystemConfig() {
		File gitExe = discoverGitExe();
		if (gitExe == null) {
			return null;
