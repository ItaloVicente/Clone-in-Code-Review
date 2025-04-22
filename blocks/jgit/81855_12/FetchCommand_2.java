	public FetchCommand setRecurseSubmodules(
			FetchRecurseSubmodulesMode recurse) {
		checkCallable();
		submoduleRecurseMode = recurse;
		return this;
	}

