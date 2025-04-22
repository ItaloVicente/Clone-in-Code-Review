	protected FS(FS src) {
		userHome = src.userHome;
		gitPrefix = src.gitPrefix;
	}

	public abstract FS newInstance();

