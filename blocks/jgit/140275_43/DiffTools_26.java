		this(repo
	}

	public DiffTools(StoredConfig config) {
		this(null
	}

	private DiffTools(Repository repo
		this.config = config.get(DiffToolConfig.KEY);
		this.gitDir = repo == null ? null : repo.getDirectory();
		this.fs = repo == null ? FS.DETECTED : repo.getFS();
		this.workTree = repo == null ? null : repo.getWorkTree();
