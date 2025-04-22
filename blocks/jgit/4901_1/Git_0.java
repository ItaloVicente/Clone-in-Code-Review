	public SubmoduleAddCommand submoduleAdd() {
		return new SubmoduleAddCommand(repo);
	}

	public SubmoduleInitCommand submoduleInit() {
		return new SubmoduleInitCommand(repo);
	}

	public SubmoduleStatusCommand submoduleStatus() {
		return new SubmoduleStatusCommand(repo);
	}

	public SubmoduleSyncCommand submoduleSync() {
		return new SubmoduleSyncCommand(repo);
	}

	public SubmoduleUpdateCommand submoduleUpdate() {
		return new SubmoduleUpdateCommand(repo);
	}

