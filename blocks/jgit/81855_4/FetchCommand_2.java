	public FetchCommand setRecurseSubmodules(boolean recurse) {
		checkCallable();
		submoduleRecurseMode = recurse ? FetchRecurseSubmodulesMode.YES
				: FetchRecurseSubmodulesMode.NO;
		return this;
	}

	public FetchCommand setRecurseSubmodules(
			FetchRecurseSubmodulesMode recurse) {
		checkCallable();
		submoduleRecurseMode = recurse;
		return this;
	}

