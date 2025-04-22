	RebaseResult(List<String> conflicts) {
		status = Status.CONFLICTS;
		currentCommit = null;
		this.conflicts = conflicts;
	}

