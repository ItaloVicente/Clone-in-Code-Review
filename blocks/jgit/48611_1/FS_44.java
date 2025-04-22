	public File getGitSystemConfig() {
		if (gitSystemConfig == null) {
			gitSystemConfig = new Holder<File>(discoverGitSystemConfig());
		}
		return gitSystemConfig.value;
	}
