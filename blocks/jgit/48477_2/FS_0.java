	public File getGitSystemConfig() {
		if (gitSystemConfig == null) {
			gitSystemConfig = new Holder<File>(discoverGitSystemConfig());
		}
		return gitSystemConfig.value;
	}

	public FS setGitSystemConfig(File configFile) {
		gitSystemConfig = new Holder<File>(configFile);
		return this;
	}

