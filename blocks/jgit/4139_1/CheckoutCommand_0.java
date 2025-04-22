	private String getShortBranchName(Ref headRef) {
		if (headRef.getTarget().getName().equals(headRef.getName()))
			return headRef.getTarget().getObjectId().getName();
		return Repository.shortenRefName(headRef.getTarget().getName());
	}

