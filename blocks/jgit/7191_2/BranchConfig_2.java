	public String getRemoteTrackingBranch() {
		String remote = getRemote();
		String mergeRef = getMergeBranch();
		if (remote == null || mergeRef == null)
			return null;

		return findRemoteTrackingBranch(remote
	}
