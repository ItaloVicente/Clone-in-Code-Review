	public B setIgnoreSystemGitConfig(boolean ignoreSystemConfig){
		this.ignoreSystemConfig = ignoreSystemConfig;
		return self();
	}

	public boolean getIgnoreSystemGitConfig(){
		return ignoreSystemConfig;
	}

	public B setIgnoreGlobalGitConfig(boolean ignoreGlobalConfig){
		this.ignoreGlobalConfig = ignoreGlobalConfig;
		return self();
	}

	public boolean getIgnoreGlobalGitConfig(){
		return ignoreGlobalConfig;
	}

