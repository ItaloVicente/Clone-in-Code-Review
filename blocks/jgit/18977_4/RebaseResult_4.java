	static RebaseResult uncommittedChanges(List<String> uncommittedChanges) {
		RebaseResult result = new RebaseResult(Status.UNCOMMITTED_CHANGES);
		result.uncommittedChanges = uncommittedChanges;
		return result;
	}

