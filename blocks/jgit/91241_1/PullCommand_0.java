	public PullCommand setRecurseSubmodules(
			FetchRecurseSubmodulesMode recurse) {
		this.submoduleRecurseMode = recurse;
		return this;
	}

	public PullCommand setRecurseSubmodules(boolean recurse) {
		setRecurseSubmodules(recurse ? FetchRecurseSubmodulesMode.YES
				: FetchRecurseSubmodulesMode.NO);
		return this;
	}

