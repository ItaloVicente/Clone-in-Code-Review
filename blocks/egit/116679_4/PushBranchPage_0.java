	private String getLocalBranchName() {
		if (ref != null && !ref.getName().startsWith(Constants.R_REMOTES)) {
			return Repository.shortenRefName(ref.getName());
		}
		return null;
	}

