	static RebaseResult conflicts(List<String> conflicts) {
		RebaseResult result = new RebaseResult(Status.CONFLICTS);
		result.conflicts = conflicts;
		return result;
	}

	static RebaseResult uncommittedChanges(List<String> uncommittedChanges) {
		RebaseResult result = new RebaseResult(Status.UNCOMMITTED_CHANGES);
		result.uncommittedChanges = uncommittedChanges;
		return result;
