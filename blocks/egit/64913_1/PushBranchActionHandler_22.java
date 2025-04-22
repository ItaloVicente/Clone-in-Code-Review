		if (repository == null) {
			return false;
		}
		if (getBranchRef(repository) == null) {
			return false;
		}
		return true;
