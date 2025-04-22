    public MergeToolManager(Repository db) {
        this(db.getFS()
    }

    public MergeToolManager(FS fs
            StoredConfig userConfig) {
        this.fs = fs;
        this.gitDir = gitDir;
        this.workTree = workTree;
        this.config = userConfig.get(MergeToolConfig.KEY);
        predefinedTools = setupPredefinedTools();
        userDefinedTools = setupUserDefinedTools(config
    }

