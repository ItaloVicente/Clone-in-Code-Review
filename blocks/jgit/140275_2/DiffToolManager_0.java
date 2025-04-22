		this(db.getFS()
	}

	public DiffToolManager(FS fs
			StoredConfig userConfig) {
		this.fs = fs;
		this.gitDir = gitDir;
		this.workTree = workTree;
		this.config = userConfig.get(DiffToolConfig.KEY);
