		this(db
	}

	public DiffTools(StoredConfig config) {
		this(null
	}

	private DiffTools(Repository db
		this.config = config.get(DiffToolConfig.KEY);
		this.gitDir = db == null ? null : db.getDirectory();
		this.fs = db == null ? FS.DETECTED : db.getFS();
		this.workTree = db == null ? null : db.getWorkTree();
