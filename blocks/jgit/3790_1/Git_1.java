	public StashCreateCommand stashCreate() {
		return new StashCreateCommand(repo);
	}

	public StashApplyCommand stashApply() {
		return new StashApplyCommand(repo);
	}

	public StashListCommand stashList() {
		return new StashListCommand(repo);
	}

