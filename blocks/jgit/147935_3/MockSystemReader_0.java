	public FileBasedConfig setUserGitConfig(FileBasedConfig userGitConfig) {
		FileBasedConfig old = this.userGitConfig;
		this.userGitConfig = userGitConfig;
		return old;
	}

	public FileBasedConfig setSystemGitConfig(FileBasedConfig systemGitConfig) {
		FileBasedConfig old = this.systemGitConfig;
		this.systemGitConfig = systemGitConfig;
		return old;
	}

