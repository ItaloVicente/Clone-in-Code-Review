		base = null;
	}

	public FileDiff(Repository repo, RevCommit tip, RevCommit base,
			DiffEntry entry) {
		repository = repo;
		diffEntry = entry;
		commit = tip;
		this.base = base;
