	RebaseResult(Set<String> uncommittedChanges) {
		status = Status.UNCOMMITTED_CHANGES;
		currentCommit = null;
		this.uncommittedChanges = uncommittedChanges;
	}

