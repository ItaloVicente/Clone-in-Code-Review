		this(repo
	}

	public MergeTools(StoredConfig config) {
		this(null
	}

	private MergeTools(Repository repo
		this.config = config.get(MergeToolConfig.KEY);
		this.gitDir = repo == null ? null : repo.getDirectory();
		this.fs = repo == null ? FS.DETECTED : repo.getFS();
		this.workTree = repo == null ? null : repo.getWorkTree();
