	public String getTrackingBranch() {
		String remote = getRemote();
		String mergeRef = getMergeBranch();
		if (remote == null || mergeRef == null)
			return null;

		if (remote.equals("."))
			return mergeRef;

		return findRemoteTrackingBranch(remote
	}

