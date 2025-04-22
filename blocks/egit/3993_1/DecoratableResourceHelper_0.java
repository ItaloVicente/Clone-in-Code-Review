
	static String getBranchStatus(Repository repo) throws IOException {
		String branchName = repo.getBranch();
		if (branchName == null)
			return null;

		StoredConfig config = repo.getConfig();
		String remoteName = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_REMOTE);
		String mergeRef = config.getString(ConfigConstants.CONFIG_BRANCH_SECTION,
				branchName, ConfigConstants.CONFIG_KEY_MERGE);
		if (remoteName == null || mergeRef == null)
			return null;

		String remoteBranchName = Repository.shortenRefName(mergeRef);
		String trackingRefName = Constants.R_REMOTES +
				remoteName + "/" + remoteBranchName;  //$NON-NLS-1$
		Ref tracking = repo.getRef(trackingRefName);
		if (tracking == null)
			return null;

		ObjectId trackingId = tracking.getObjectId();
		Ref head = repo.getRef(Constants.HEAD);

		ObjectId headId = head.getObjectId();
		if (trackingId.equals(headId))
			return ""; //$NON-NLS-1$
		else
			return "*"; //$NON-NLS-1$
	}
