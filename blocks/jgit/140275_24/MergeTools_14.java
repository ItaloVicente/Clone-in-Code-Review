		this(db
	}

	public MergeTools(StoredConfig config) {
		this(null
	}

	private MergeTools(Repository db
		this.config = config.get(MergeToolConfig.KEY);
		this.gitDir = db == null ? null : db.getDirectory();
		this.fs = db == null ? FS.DETECTED : db.getFS();
		this.workTree = db == null ? null : db.getWorkTree();
