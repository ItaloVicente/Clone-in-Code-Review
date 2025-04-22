	protected boolean containsHead(Repository repository) {
		try {
			return repository != null ? repository.resolve(Constants.HEAD) != null
					: false;
		} catch (Exception e) {
		}

		return false;
	}

	protected boolean isLocalBranchCheckedout(Repository repository) {
