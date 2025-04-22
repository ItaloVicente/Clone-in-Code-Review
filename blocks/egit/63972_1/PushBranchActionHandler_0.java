		if (repository == null) {
			return false;
		}
		Ref head = getBranchRef(repository);
		if (head == null) {
			return false;
		}
		return true;
