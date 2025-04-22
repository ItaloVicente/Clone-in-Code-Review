	public File getGitSystemConfig() {
		return gitSystemConfig.value;
	}

	public FS setGitSystemConfig(File configFile) {
		gitSystemConfig = new Holder<File>(configFile);
		return this;
	}

