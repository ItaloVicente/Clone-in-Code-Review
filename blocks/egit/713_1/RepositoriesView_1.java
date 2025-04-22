	private boolean isRefCheckedOut(Repository repository, String refName) {
		String branchName;
		String compareString;

		try {
			branchName = repository.getFullBranch();
			if (branchName == null)
				return false;
			if (refName.startsWith(Constants.R_HEADS)) {
				compareString = refName;
			} else if (refName.startsWith(Constants.R_TAGS)) {
				compareString = repository.mapTag(refName).getObjId().getName();
			} else if (refName.startsWith(Constants.R_REMOTES)) {
				compareString = repository.mapCommit(refName).getCommitId()
						.getName();
			} else {
				return false;
			}
		} catch (IOException e1) {
			return false;
		}

		return compareString.equals(branchName);
	}

